package domain.pos.table.implement;

import java.util.Optional;

import org.springframework.stereotype.Component;

import domain.pos.table.entity.Table;
import domain.pos.table.repository.TableRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TableReader {
	private final TableRepository tableRepository;

	public Optional<Table> findLockTableById(Long queryTableId) {
		return tableRepository.findById(queryTableId);
	}
}
