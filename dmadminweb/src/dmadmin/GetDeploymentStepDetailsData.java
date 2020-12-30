/*
 *
 *  Ortelius for Microservice Configuration Mapping
 *  Copyright (C) 2017 Catalyst Systems Corporation DBA OpenMake Software
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package dmadmin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;

import dmadmin.json.JSONObject;
import dmadmin.model.Deployment;


/**
 * Servlet implementation class GetDeploymentStepDetailsData
 */
public class GetDeploymentStepDetailsData
	extends HttpServletBase
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDeploymentStepDetailsData() {
        super();
    }
    
    public void handleRequest(DMSession session, boolean isPost,
   			HttpServletRequest request, HttpServletResponse response)
   		throws ServletException, IOException
   	{
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		int depid = ServletUtils.getIntParameter(request, "id");
		int stepid = ServletUtils.getIntParameter(request, "step");
		int instid = ServletUtils.getIntParameter(request, "inst");
	
		Deployment dep = session.getDeployment(depid, false);
		
		JSONObject obj = new JSONObject();
		obj.add("readOnly", true);
		obj.add("data", dep.getStepDetails(stepid, instid).getStepJSON());
		
		String ret = obj.toString();
		
		out.println(ret);
		System.out.println(ret);	
   	}
}
