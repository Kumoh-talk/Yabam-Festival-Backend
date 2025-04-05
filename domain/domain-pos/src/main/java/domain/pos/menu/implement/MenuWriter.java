package domain.pos.menu.implement;

import org.springframework.stereotype.Component;

import domain.pos.menu.entity.Menu;
import domain.pos.menu.entity.MenuInfo;
import domain.pos.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MenuWriter {
	private final MenuRepository menuRepository;

	public Menu postMenu(Long storeId, Long userId, Long menuCategoryId, MenuInfo menuInfo) {
		return menuRepository.postMenu(storeId, userId, menuCategoryId, menuInfo);
	}

	public MenuInfo patchMenu(MenuInfo patchMenuInfo) {
		return menuRepository.patchMenu(patchMenuInfo);
	}

	public MenuInfo patchMenuOrder(Long storeId, Long menuCategoryId, Long menuId, int order) {
		return menuRepository.patchMenuOrder(storeId, menuCategoryId, menuId, order);
	}

	public void deleteMenu(Long storeId, Long menuCategoryId, Long menuId) {
		menuRepository.deleteMenu(storeId, menuCategoryId, menuId);
	}

}
