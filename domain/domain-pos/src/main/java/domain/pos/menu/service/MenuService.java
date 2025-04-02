package domain.pos.menu.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import domain.pos.menu.entity.Menu;
import domain.pos.menu.entity.MenuInfo;
import domain.pos.menu.implement.MenuCategoryValidator;
import domain.pos.menu.implement.MenuReader;
import domain.pos.menu.implement.MenuWriter;
import domain.pos.store.implement.StoreReader;
import domain.pos.store.implement.StoreValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {
	private final StoreValidator storeValidator;
	private final MenuCategoryValidator menuCategoryValidator;
	private final StoreReader storeReader;
	private final MenuReader menuReader;
	private final MenuWriter menuWriter;

	public Menu postMenu(Long storeId, Long userId, Long menuCategoryId, MenuInfo menuInfo) {
		storeValidator.validateStoreOwner(storeId, userId);
		menuCategoryValidator.validateMenuCategory(menuCategoryId);
		return menuWriter.postMenu(storeId, userId, menuCategoryId, menuInfo);
	}

	public Slice<MenuInfo> getMenuSlice(Pageable pageable, Long lastMenuId, Long storeId, Long menuCategoryId) {
		storeReader.readSingleStore(storeId)
			.orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_STORE));
		menuCategoryValidator.validateMenuCategory(menuCategoryId);

		MenuInfo lastMenuInfo = null;
		if (lastMenuId != null) {
			lastMenuInfo = menuReader.getMenuInfo(lastMenuId)
				.orElseThrow(() -> new ServiceException(ErrorCode.MENU_NOT_FOUND));
		}
		return menuReader.getMenuSlice(pageable, lastMenuInfo, menuCategoryId);
	}

}
