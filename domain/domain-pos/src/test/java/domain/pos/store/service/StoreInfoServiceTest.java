package domain.pos.store.service;

import static fixtures.store.StoreFixture.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import base.ServiceTest;
import domain.pos.member.entity.Owner;
import domain.pos.member.implement.OwnerValidator;
import domain.pos.store.entity.StoreInfo;
import domain.pos.store.implement.StoreWriter;

@ExtendWith(MockitoExtension.class)
class StoreInfoServiceTest extends ServiceTest {

	@Mock
	private OwnerValidator ownerValidator;

	@Mock
	private StoreWriter storeWriter;

	@InjectMocks
	private StoreService storeService;

	@Nested
	@DisplayName("store 생성")
	class createStoreInfo {
		private static final Long SAVED_STORE_ID = 1L;

		@Test
		void 성공() {
			// given
			StoreInfo requestStoreInfo = CREATE_REQUEST_STORE_INFO();
			Owner owner = new Owner();

			doReturn(SAVED_STORE_ID)
				.when(storeWriter).createStore(owner, requestStoreInfo);

			// when
			Long storeId = storeService.createStore(owner, requestStoreInfo);
			// then
			assertSoftly(softly -> {
				softly.assertThat(storeId).isEqualTo(SAVED_STORE_ID);

				verify(ownerValidator)
					.validateOwner(owner);
				verify(storeWriter)
					.createStore(owner, requestStoreInfo);
			});
		}

		@Test
		void 실패_점주_유효성검사() {
			// given
			StoreInfo requestStoreInfo = CREATE_REQUEST_STORE_INFO();
			Owner owner = new Owner();

			doThrow(new ServiceException(ErrorCode.NOT_VALID_OWNER))
				.when(ownerValidator).validateOwner(owner);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(() -> storeService.createStore(owner, requestStoreInfo))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_VALID_OWNER);
				verify(ownerValidator)
					.validateOwner(owner);
				verify(storeWriter, never())
					.createStore(any(), any());
			});
		}
	}

}
