package domain.pos.store.implement;

import java.util.Optional;

import org.springframework.stereotype.Component;

import domain.pos.store.entity.Store;
import domain.pos.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StoreReader {
	private final StoreRepository storeRepository;

	public Optional<Store> readSingleStore(Long storeId) {
		return storeRepository.findStoreByStoreId(storeId);
	}
}
