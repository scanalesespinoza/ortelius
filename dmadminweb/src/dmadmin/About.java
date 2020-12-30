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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmadmin.json.JSONObject;

/**
 * Servlet implementation class GetSchemaVersion
 */
public class About extends HttpServletBase
{
 private static final long serialVersionUID = 1L;

 /**
  * @see HttpServlet#HttpServlet()
  */
 public About()
 {
  super();
 }

 @Override
 public void handleRequest(DMSession session, boolean isPost, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {
  int ver = session.getSchemaVersion();
  String lc = session.getLicenseKey();
  String dbuid = session.getDatabaseId();
  request.setAttribute("schemaver", ver);
  request.setAttribute("license", lc);
  request.setAttribute("dbuid", dbuid);
  
  String lickey = request.getParameter("k");
  
  if (lickey != null)
  {
   session.updateLicense(lickey);
   response.getOutputStream().println(new JSONObject().toString());
  }
  else
   request.getRequestDispatcher("/WEB-INF/About.jsp").forward(request, response);
 }
}
