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
package dmadmin.model;

import dmadmin.DMSession;
import dmadmin.ObjectType;
import dmadmin.PropertyDataSet;
import dmadmin.SummaryChangeSet;
import dmadmin.SummaryField;
import dmadmin.json.BooleanField;
import dmadmin.json.IJSONSerializable;
import dmadmin.json.LinkField;

public class Domain
	extends DMObject
{
	private static final long serialVersionUID = 1327862378913381548L;

	private String m_parentDomain;
	private Engine m_engine;
	private boolean m_lifecycle;
	
	public Domain() {
	}
	
	public Domain(DMSession sess, int id, String name) {
		super(sess, id, name);
	}
	
	public Engine getEngine()  { return m_engine; }
	public void setEngine(Engine engine)  { m_engine = engine; }
	public Engine findNearestEngine() {
		if(m_engine != null) {
			return m_engine;
		}
		Domain parent = getDomain();
		if(parent != null) {
			return parent.findNearestEngine();
		}
		return null;
	}
	
	public String getFullDomain()
	{
	//	if (!m_session.ValidDomain(getId())) return "";
		
		return m_session.getFQDN(getId());
		
//		String fqdom = getName();
//		Domain parent = getDomain();
//		while (parent != null && m_session.ValidDomain(parent.getId())) {
//			fqdom  = parent.getName() + "." + fqdom;
//			if (parent.getId()==m_session.UserBaseDomain()) break;
//			parent = parent.getDomain();
//		}
//		return fqdom;
	}
	
	public void setLifecycle(boolean x) { m_lifecycle = x; }
	public boolean getLifecycle() { return m_lifecycle; }
	
	@Override
	public ObjectType getObjectType() {
		return ObjectType.DOMAIN;
	}

	@Override
	public String getDatabaseTable() {
		return "dm_domain";
	}

	@Override
	public String getForeignKey() {
		return "domainid";
	}

	public void setParentDomain(String domain) {
		m_parentDomain = domain;
	}
	public String getParentDomain() {
		return m_parentDomain;
	}

	@Override
	public IJSONSerializable getSummaryJSON() {
		PropertyDataSet ds = new PropertyDataSet();
  Domain dom = getDomain();
  if (dom == null)
    ds.addProperty(SummaryField.DOMAIN_FULLNAME, "Full Domain", "GLOBAL");
  else
   ds.addProperty(SummaryField.DOMAIN_FULLNAME, "Full Domain", dom.getFullDomain());
		ds.addProperty(SummaryField.NAME, "Name", getName());
//		ds.addProperty(SummaryField.READ_ONLY, "Parent Domain", new ReadOnlyTextField(m_parentDomain));
		ds.addProperty(SummaryField.SUMMARY, "Summary", getSummary());
		ds.addProperty(SummaryField.OWNER, "Owner", (m_owner != null) ? m_owner.getLinkJSON()
				: ((m_ownerGroup != null) ? m_ownerGroup.getLinkJSON() : null));
		addCreatorModifier(ds);
		ds.addProperty(SummaryField.ENGINE_HOSTNAME, "Engine", (m_engine != null) ? m_engine.getLinkJSON() : null);
		
		Domain parent = this.getDomain();
		if (parent != null) {
			if (!parent.m_lifecycle) {
				ds.addProperty(SummaryField.DOMAIN_LIFECYCLE, "LifeCycle Domain", new BooleanField(m_lifecycle));
			}
		} else {
			ds.addProperty(SummaryField.DOMAIN_LIFECYCLE, "LifeCycle Domain", new BooleanField(m_lifecycle));
		}
		return ds.getJSON();
	}

	@Override
	public boolean updateSummary(SummaryChangeSet changes) {
		return m_session.updateDomain(this, changes);
	}
	
	@Override
 public IJSONSerializable getLinkJSON() {
  return new LinkField(getObjectType(), m_id, this.getFullDomain());
 }
}
