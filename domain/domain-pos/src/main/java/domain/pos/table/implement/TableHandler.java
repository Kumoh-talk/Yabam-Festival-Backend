package domain.pos.table.implement;

import java.util.Optional;

import org.springframework.stereotype.Component;

import domain.pos.store.entity.Store;
import domain.pos.table.entity.Table;
import domain.pos.table.repository.TableRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TableHandler {
	private final TableRepository tableRepository;

	public Table createTable(Store responStore, Integer queryTableNumber) {
		return tableRepository.createTable(responStore, queryTableNumber);
	}

	public Optional<Object> exitsTable(Store responStore, Integer queryTableNumber) {
		return tableRepository.findTableByStoreAndTableNum(responStore, queryTableNumber);
	}
}
