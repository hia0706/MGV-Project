package kr.co.mgv.theater.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.mgv.favoritetheater.vo.FavoriteTheater;
import kr.co.mgv.theater.service.TheaterService;
import kr.co.mgv.theater.vo.Location;
import kr.co.mgv.theater.vo.Theater;
import kr.co.mgv.user.vo.User;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/theater")
@RequiredArgsConstructor
public class TheaterController {

	private final TheaterService theaterService;

	
    @GetMapping({"/", "", "/list"})
    public String home() {
    	
        return "view/theater/home";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(defaultValue = "1") int brchNo, Model model) {
    	Theater theater = theaterService.getTheaterDetail(brchNo);
    	model.addAttribute("theater", theater);
        return "view/theater/detail";
    }
    
    @GetMapping("/theaterList")
    @ResponseBody
    public List<Location> theaterList(){
    	List<Location> locations = theaterService.getTheaters();
    	return locations;
    }
    
    @GetMapping("/favorite")
    @ResponseBody
    public List<Theater> favoriteTheaters(@AuthenticationPrincipal User user){
    	if(user == null) {
    		return null;
    	}else {
    		String userId = user.getId();
    		List<Theater> theaters = theaterService.getFavoriteTheaters(userId);
    		return theaters;
    	}
    }
    
    @PostMapping("/favorite")
    @ResponseBody
    public String registrationFavoriteTheaters(@AuthenticationPrincipal User user, @RequestBody List<FavoriteTheater> favoriteTheaters ) {
    	if(user == null) {
    		return "undefined";
    	}else {
    		String userId = user.getId();
    		theaterService.registFavoriteTheaters(userId, favoriteTheaters);
    		return "success";
    		
    	}
    	
    }
    
    @PostMapping("/favorite/registfavorite")
    @ResponseBody
    public String registrationFavoriteTheater(@AuthenticationPrincipal User user, @RequestBody FavoriteTheater favoriteTheater ) {
    	if(user == null) {
    		return "undefined";
    	}else {
    		String userId = user.getId();
    		return theaterService.registFavoriteTheater(userId, favoriteTheater);
    		
    	}
    	
    }

}
