package io.github.xinyangpan.kafka.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KfMsg {

	private Long time;
	private String message;

	public KfMsg(String message) {
		this.time = System.currentTimeMillis();
		this.message = message;
	}

}
