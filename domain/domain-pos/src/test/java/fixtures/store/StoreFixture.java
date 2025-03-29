package fixtures.store;

import domain.pos.member.entity.Owner;
import domain.pos.store.entity.Store;
import domain.pos.store.entity.StoreInfo;

public class StoreFixture {
	// 가게 고유 ID
	private static final Long STORE_ID = 1L;

	// 가게 점주
	private static final Owner GENERAL_STORE_OWNER = new Owner();

	// 가게 정보
	private static final StoreInfo GENERAL_STORE_INFO = new StoreInfo();

	public static StoreInfo CREATE_REQUEST_STORE_INFO() {
		return new StoreInfo();
	}

	public static Store GENERAL_STORE() {
		return new Store(
			STORE_ID,
			GENERAL_STORE_INFO,
			GENERAL_STORE_OWNER
		);
	}
}
