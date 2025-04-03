package fixtures.store;

import static fixtures.member.OwnerFixture.*;
import static fixtures.store.StoreInfoFixture.*;

import domain.pos.member.entity.Owner;
import domain.pos.store.entity.Store;
import domain.pos.store.entity.StoreInfo;

public class StoreFixture {
	// 가게 고유 ID
	private static final Long STORE_ID = 1L;

	// 가게 활성화 여부
	private static final Boolean IS_OPEN = true;
	private static final Boolean IS_CLOSED = false;

	// 가게 점주
	private static final Owner GENERAL_STORE_OWNER = GENERAL_OWNER();

	// 가게 정보
	private static final StoreInfo GENERAL_STORE_INFO = GENERAL_STORE_INFO();
	private static final StoreInfo GENERAL_CHANGED_STORE_INFO = CHANGED_GENERAL_STORE_INFO();

	public static Store GENERAL_STORE() {
		return new Store(
			STORE_ID,
			IS_CLOSED,
			GENERAL_STORE_INFO,
			GENERAL_STORE_OWNER
		);
	}

	public static Store CHANGED_GENERAL_STORE() {
		return new Store(
			STORE_ID,
			IS_CLOSED,
			GENERAL_CHANGED_STORE_INFO,
			GENERAL_STORE_OWNER
		);
	}

	public static Store GENERAL_OPEN_STORE() {
		return new Store(
			STORE_ID,
			IS_OPEN,
			GENERAL_STORE_INFO,
			GENERAL_STORE_OWNER
		);
	}

	public static Store CUSTOM_STORE(Long storeId, StoreInfo storeInfo, Owner owner) {
		return new Store(
			storeId,
			IS_CLOSED,
			storeInfo,
			owner
		);
	}
}
