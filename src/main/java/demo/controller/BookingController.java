package demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.common.constants.RestURIConstants;
import demo.common.json.CommonJson;
import demo.common.utils.GeneralUtil;
import demo.db.main.persistence.domain.BookingDAO;
import demo.service.BookingService;

@RestController
@ControllerAdvice
public class BookingController extends ApiController {

	@Autowired
	private BookingService bookingService;
	
	@RequestMapping(value = RestURIConstants.BOOKINGS , method = RequestMethod.GET)
	public CommonJson getAllBookings(HttpServletRequest request, @RequestParam("userId") String userId) throws Exception {
//		jsonSchemaValidate(request,userId);
		CommonJson bookings = new CommonJson();

		List<BookingDAO>  listOfBookings = bookingService.getAllBookings();

		return bookings.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL)
				.set("bookings", listOfBookings)
				.set("success", Boolean.TRUE);

	}
	
	

	@RequestMapping(value = RestURIConstants.USERBOOKINGS, method = RequestMethod.GET)
	public CommonJson getUserBookings(HttpServletRequest request, @RequestParam("userId") String userId) throws Exception {
//		jsonSchemaValidate(request,userId);
		CommonJson bookings = new CommonJson();

		List<BookingDAO> listOfBookings = bookingService.getUserBookings(userId);

		return bookings.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL)
				.set("bookings", listOfBookings)
				.set("success", Boolean.TRUE);

	}

}
