package pillarkata.rest.project;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/calculate")
public class BabySitterRestful {

	@GET
	@Path("{salary}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String calculateSalary(@PathParam("salary") String salary) {
		System.out.println("Ann Arbor, Michigan");
		int totalSalary = 0;
		try {
			JSONObject jsonOrder = new JSONObject(salary);
			long checkInTime = jsonOrder.getLong("checkintime");
			long checkOutTime = jsonOrder.getLong("checkouttime");
			long minTime = jsonOrder.getLong("mintime");
			long maxTime = jsonOrder.getLong("maxtime");
			BabySitterBO bo = new BabySitterBO(checkInTime, checkOutTime, minTime, maxTime);
			totalSalary = bo.calculateTotalSalary();
			System.out.println(jsonOrder);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"totalSalary:\""+"Something went wrong, Please try again}";
		}
		return "{\"totalSalary\":" + totalSalary + "}";
	}
}