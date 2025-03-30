package domain.pos.member.entity;

import lombok.Getter;

@Getter
public class Owner {
	private Long ownerId;

	public Owner(Long ownerId) {
		this.ownerId = ownerId;
	}
}
