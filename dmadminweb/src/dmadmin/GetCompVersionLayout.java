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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;

import dmadmin.json.JSONArray;
import dmadmin.json.JSONObject;
import dmadmin.model.Component;

/**
 * Servlet implementation class GetCompVersionLayout
 */
public class GetCompVersionLayout extends HttpServletBase {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCompVersionLayout() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void handleRequest(DMSession session, boolean isPost,
   			HttpServletRequest request, HttpServletResponse response)
   		throws ServletException, IOException
   	{
    	response.setContentType("application/json;charset=UTF-8");
		int compid = ServletUtils.getIntParameter(request, "compid");
		PrintWriter out = response.getWriter();
		Component comp = session.getComponent(compid,true);
		boolean bReadOnly = !comp.isUpdatable();
		
		List <Component> compvers = session.getComponentVersions(comp);
		
		JSONArray arr1 = new JSONArray();
		for(Component c: compvers) {
			JSONObject vobj = new JSONObject();
			vobj.add("nodeid", c.getId());
			vobj.add("name", c.getName());
			vobj.add("suffix", c.getIconSuffix());
			vobj.add("xpos", c.getXpos());
			vobj.add("ypos", c.getYpos());
			vobj.add("summary", c.getSummary());
			arr1.add(vobj);
		}

		JSONArray arr2 = new JSONArray();
		for(Component cl: compvers) {
			JSONObject vlobj = new JSONObject();
			vlobj.add("nodefrom", cl.getPredecessorId());
			vlobj.add("nodeto", cl.getId());
			vlobj.add("branch",cl.getLabel());
			arr2.add(vlobj);
		}

		JSONObject obj = new JSONObject();
		obj.add("Nodes", arr1);
		obj.add("Links", arr2);
		obj.add("LinkCount", compvers.size());
		obj.add("NodeCount", compvers.size());
		obj.add("readOnly", bReadOnly);
		String ret = obj.getJSON();
		System.out.println(ret);
		out.println(ret);
   	}
}
