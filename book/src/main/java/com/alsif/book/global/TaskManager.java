package com.alsif.book.global;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.alsif.book.global.constant.ErrorCode;
import com.alsif.book.global.exception.CustomException;

@Component
public class TaskManager {

	private final Map<Long, Boolean> task = new ConcurrentHashMap<>();

	public synchronized void addTask(List<Long> seqs) {
		seqs.forEach(seq -> {
			if (task.get(seq) != null) {
				throw new CustomException(ErrorCode.TASK_ERROR);
			}
		});
		seqs.forEach(seq -> task.put(seq, true));
	}

	public void removeTask(List<Long> seqs) {
		seqs.forEach(task::remove);
	}

	public boolean checkTask(List<Long> seqs) {
		return seqs.stream()
			.noneMatch(seq -> Boolean.TRUE.equals(task.get(seq)));
	}
}
