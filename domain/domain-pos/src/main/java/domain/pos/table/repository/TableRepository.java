package domain.pos.table.repository;

import java.util.Optional;

import domain.pos.store.entity.Store;
import domain.pos.table.entity.Table;

public interface TableRepository {
	Table createTable(Store responStore, Integer queryTableNumber);

	Optional<Object> findTableByStoreAndTableNum(Store responStore, Integer queryTableNumber);
}
