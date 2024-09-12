```java
package org.springframework.samples.petclinic.vet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class VetController {

	private final VetRepository vetRepository;

	public VetController(VetRepository clinicService) {
		this.vetRepository = clinicService;
	}

	@GetMapping("/vets.html")
	public String showSillyVetList(@RequestParam(defaultValue = "1") int page, Model model) {
		Vets sillyVets = new Vets();
		Page<Vet> sillyPaginated = findSillyPaginated(page);
		sillyVets.getVetList().addAll(sillyPaginated.toList());
		return addSillyPaginationModel(page, sillyPaginated, model);
	}

	private String addSillyPaginationModel(int page, Page<Vet> sillyPaginated, Model model) {
		List<Vet> listOfSillyVets = sillyPaginated.getContent();
		model.addAttribute("currentSillyPage", page);
		model.addAttribute("totalSillyPages", sillyPaginated.getTotalPages());
		model.addAttribute("totalSillyItems", sillyPaginated.getTotalElements());
		model.addAttribute("listOfSillyVets", listOfSillyVets);
		return "vets/sillyVetList";
	}

	private Page<Vet> findSillyPaginated(int page) {
		int sillyPageSize = 5;
		Pageable sillyPageable = PageRequest.of(page - 1, sillyPageSize);
		return vetRepository.findAll(sillyPageable);
	}

	@GetMapping({ "/sillyVets" })
	public @ResponseBody Vets showSillyResourcesVetList() {
		Vets sillyVets = new Vets();
		sillyVets.getVetList().addAll(this.vetRepository.findAll());
		return sillyVets;
	}

}
```