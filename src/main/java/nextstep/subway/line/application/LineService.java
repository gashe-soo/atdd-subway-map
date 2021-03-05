package nextstep.subway.line.application;

import nextstep.subway.exception.ResourceNotFoundException;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.dto.LineRequest;
import nextstep.subway.line.dto.LineResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LineService {
    private LineRepository lineRepository;

    public LineService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    public LineResponse saveLine(LineRequest request) {
        Line persistLine = lineRepository.save(request.toLine());
        return LineResponse.of(persistLine);
    }

    @Transactional(readOnly = true)
    public List<LineResponse> findAllLines() {
        List<Line> lines = lineRepository.findAll();

        return lines.stream()
                .map(line -> LineResponse.of(line))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LineResponse findById(Long id) {
        return lineRepository.findById(id)
                .map(line -> LineResponse.of(line))
                .orElseThrow(ResourceNotFoundException::new);
    }

    public LineResponse updateById(Long id, LineRequest request) {
        Line line = lineRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        line.update(request.toLine());

        Line persistLine = lineRepository.save(line);
        return LineResponse.of(persistLine);
    }

    public void deleteById(Long id) {
        Line line = lineRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        lineRepository.delete(line);
    }
}
