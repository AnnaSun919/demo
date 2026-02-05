package demo.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.common.json.CommonJson;
import demo.service.BookingService;
import demo.common.utils.GeneralUtil;
import demo.db.main.persistence.domain.BookingDAO;

@RestController
@ControllerAdvice
public class BookingController extends ApiController {
	
	@Autowired
	private BookingService bookingService;

	@RequestMapping(value = "/bookings", method = RequestMethod.GET)
	public CommonJson getBookings(HttpServletRequest request, @RequestParam("userId") String userId) throws Exception {
//		jsonSchemaValidate(request,userId);
		CommonJson bookings = new CommonJson();

		BookingDAO listOfBookings = bookingService.getBookings(userId);
		
		return bookings.set("errCode",GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL ).set("listOfBooking", listOfBookings);

	}

}
