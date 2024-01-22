package com.sabit.rabbitmqmqtt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartController {

	@GetMapping("/")
	public String getPieChart(Model model) {
		return "charts";
	}

}
