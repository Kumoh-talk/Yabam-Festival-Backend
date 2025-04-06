package domain.pos.menu.implement;

import org.springframework.stereotype.Component;

import domain.pos.member.entity.UserPassport;
import domain.pos.menu.entity.Menu;
import domain.pos.menu.entity.MenuInfo;
import domain.pos.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MenuWriter {
	private final MenuRepository menuRepository;

	public Menu postMenu(Long storeId, UserPassport userPassport, Long menuCategoryId, MenuInfo menuInfo) {
		return menuRepository.postMenu(storeId, userPassport, menuCategoryId, menuInfo);
	}

	public MenuInfo patchMenu(MenuInfo patchMenuInfo) {
		return menuRepository.patchMenu(patchMenuInfo);
	}

	public MenuInfo patchMenuOrder(Long storeId, Long menuCategoryId, Long menuId, int patchOrder) {
		return menuRepository.patchMenuOrder(storeId, menuCategoryId, menuId, patchOrder);
	}

	public void deleteMenu(Long storeId, Long menuCategoryId, Long menuId) {
		menuRepository.deleteMenu(storeId, menuCategoryId, menuId);
	}

}
