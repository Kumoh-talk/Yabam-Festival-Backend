package domain.pos.table.implement;

import org.springframework.stereotype.Component;

import domain.pos.table.entity.Table;
import domain.pos.table.repository.TableRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TableWriter {
	private final TableRepository tableRepository;

	public Table changeTableActiveStatus(boolean isActive, Table savedInActiveTable) {
		Table changeActiveTable = tableRepository.changeTableActiveStatus(isActive, savedInActiveTable);
		return changeActiveTable;
	}
}
