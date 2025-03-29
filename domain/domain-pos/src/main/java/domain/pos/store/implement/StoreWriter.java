package domain.pos.store.implement;

import org.springframework.stereotype.Component;

import domain.pos.member.entity.Owner;
import domain.pos.store.entity.Store;
import domain.pos.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StoreWriter {
	private final StoreRepository storeRepository;

	public Long createStore(Owner owner, Store createRequestStore) {
		return storeRepository.createStore(owner, createRequestStore);
	}

}
