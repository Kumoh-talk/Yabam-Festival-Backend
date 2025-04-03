package domain.pos.store.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.Getter;

@Getter
public class Sale {
	private final Long saleId;
	private final LocalDateTime openDateTime;
	private final Optional<LocalDateTime> closeDateTime;
	private final Store store;

	private Sale(Long saleId, LocalDateTime openDateTime, LocalDateTime closeDateTime, Store store) {
		this.saleId = saleId;
		this.openDateTime = openDateTime;
		this.closeDateTime = Optional.ofNullable(closeDateTime);
		this.store = store;
	}

	public static Sale of(Long saleId,
		LocalDateTime openDateTime,
		LocalDateTime closeDateTime,
		Store store) {
		return new Sale(saleId,
			openDateTime,
			closeDateTime,
			store);
	}

}
