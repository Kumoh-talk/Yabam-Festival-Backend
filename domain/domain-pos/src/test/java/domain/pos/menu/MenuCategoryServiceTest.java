package domain.pos.menu;

import static fixtures.menu.MenuCategoryFixture.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import base.ServiceTest;
import domain.pos.menu.entity.MenuCategory;
import domain.pos.menu.implement.MenuCategoryWriter;
import domain.pos.menu.service.MenuCategoryService;
import domain.pos.store.implement.StoreValidator;

public class MenuCategoryServiceTest extends ServiceTest {
	@Mock
	private StoreValidator storeValidator;
	@Mock
	private MenuCategoryWriter menuCategoryWriter;
	@InjectMocks
	private MenuCategoryService menuCategoryService;

	@Nested
	@DisplayName("메뉴 카테고리 생성")
	class postMenuCategory {
		private final Long storeId = 1L;
		private final Long userId = 2L;
		private final String categoryName = "categoryName";

		private final Long menuCategoryId = 1L;

		@Test
		void 메뉴_카테고리_생성_성공() {
			// given
			MenuCategory menuCategory = CUSTOM_MENU_CATEGORY(menuCategoryId, categoryName);

			BDDMockito.given(menuCategoryWriter.postMenuCategory(categoryName))
				.willReturn(menuCategory);

			// when
			MenuCategory serviceMenuCategory = menuCategoryService.postMenuCategory(storeId, userId, categoryName);

			// then
			assertSoftly(softly -> {
				softly.assertThat(serviceMenuCategory.getMenuCategoryId()).isEqualTo(menuCategoryId);
				softly.assertThat(serviceMenuCategory.getMenuCategoryName()).isEqualTo(categoryName);
			});
		}

		@Test
		void 주점_조회_실패() {
			// given
			doThrow(new ServiceException(ErrorCode.NOT_FOUND_STORE))
				.when(storeValidator)
				.validateStoreOwner(storeId, userId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(() -> menuCategoryService.postMenuCategory(storeId, userId, categoryName))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_STORE);
				verify(storeValidator)
					.validateStoreOwner(storeId, userId);
				verify(menuCategoryWriter, never())
					.postMenuCategory(categoryName);
			});
		}

		@Test
		void 점주_인증_실패() {
			// given
			doThrow(new ServiceException(ErrorCode.NOT_EQUAL_STORE_OWNER))
				.when(storeValidator)
				.validateStoreOwner(storeId, userId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(() -> menuCategoryService.postMenuCategory(storeId, userId, categoryName))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_EQUAL_STORE_OWNER);
				verify(storeValidator)
					.validateStoreOwner(storeId, userId);
				verify(menuCategoryWriter, never())
					.postMenuCategory(categoryName);
			});
		}
	}
}
