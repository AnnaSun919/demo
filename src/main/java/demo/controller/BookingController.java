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

	@RequestMapping(value = RestURIConstants.ALLUSERBOOKINGS, method = RequestMethod.GET)
	public CommonJson getAllBookings(HttpServletRequest request) throws Exception {
//		jsonSchemaValidate(request,userId);
		CommonJson bookings = new CommonJson();

		List<CommonJson> listOfBookings = bookingService.getAllUserFutureBookings();

		return bookings.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("bookings", listOfBookings)
				.set("success", Boolean.TRUE);

	}

	@RequestMapping(value = RestURIConstants.MYBOOKINGS, method = RequestMethod.GET)
	public CommonJson getUserBookings(HttpServletRequest request, @RequestParam("userId") String userId)
			throws Exception {
//		jsonSchemaValidate(request,userId);
		CommonJson bookings = new CommonJson();

		List<CommonJson> listOfBookings = bookingService.getMyBookings(userId);

		return bookings.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("bookings", listOfBookings)
				.set("success", Boolean.TRUE);

	}

	@RequestMapping(value = RestURIConstants.CANCELBOOKING, method = RequestMethod.PATCH)
	public CommonJson cancelUserBookings(HttpServletRequest request, @RequestParam("bookingId") String bookingId)
			throws Exception {
//		jsonSchemaValidate(request,userId);
		CommonJson bookings = new CommonJson();

		BookingDAO result = bookingService.cancelBooking(bookingId);

		return bookings.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("booking", result).set("success",
				Boolean.TRUE);

	}

	@RequestMapping(value = RestURIConstants.PENDINGAPPROVAL , method = RequestMethod.GET)
	public CommonJson getAllPendingAprovalBooking(HttpServletRequest request) throws Exception {
//		jsonSchemaValidate(request,userId);
		CommonJson bookings = new CommonJson();

		List<CommonJson> result = bookingService.getAllPendingAprovalBooking();

		return bookings.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("bookings", result).set("success",
				Boolean.TRUE);

	}
	
	@RequestMapping(value = RestURIConstants.APPROVEBOOKING, method = RequestMethod.PATCH)
	public CommonJson approveBooking(HttpServletRequest request, @RequestParam("bookingId") String bookingId)
			throws Exception {
//		jsonSchemaValidate(request,userId);
		CommonJson bookings = new CommonJson();

		BookingDAO result = bookingService.approveBooking(bookingId);

		return bookings.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("booking", result).set("success",
				Boolean.TRUE);

	}
	
	@RequestMapping(value = RestURIConstants.REJECTBOOKING , method = RequestMethod.PATCH)
	public CommonJson rejectBooking(HttpServletRequest request, @RequestParam("bookingId") String bookingId)
			throws Exception {
//		jsonSchemaValidate(request,userId);
		CommonJson bookings = new CommonJson();

		BookingDAO result = bookingService.rejectBooking(bookingId);

		return bookings.set("errCode", GeneralUtil.ERRCODE_REQUEST_SUCCESSFUL).set("booking", result).set("success",
				Boolean.TRUE);

	}

}
