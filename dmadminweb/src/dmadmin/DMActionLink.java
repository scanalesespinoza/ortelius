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

public class DMActionLink implements java.io.Serializable {
	private static final long serialVersionUID = -5362995729983900561L;
	private int m_flowid;
	private int m_nodefrom;
	private int m_nodeto;
	private int m_pos;

	
	public DMActionLink() {
		m_flowid=0;
		m_nodefrom=0;
		m_nodeto=0;
		m_pos=0;
    }
	public void setFlowID(int id) {
		m_flowid = id;
	}
	public void setNodeFrom(int node) {
		m_nodefrom = node;
	}
	public void setNodeTo(int node) {
		m_nodeto = node;
	}
	public void setPos(int pos) {
		m_pos = pos;
	}
	
	public int getFlowID() {
		return m_flowid;
	}
	public int getNodeFrom() {
		return m_nodefrom;
	}
	public int getNodeTo() {
		return m_nodeto;
	}
	public int getPos() {
		return m_pos;
	}
}
