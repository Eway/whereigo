/*
  * This file is part of WhereYouGo.
  *
  * WhereYouGo is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  *
  * WhereYouGo is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with WhereYouGo.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright (C) 2012 Menion <whereyougo@asamm.cz>
  */ 

package menion.android.whereyougo.geoData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import menion.android.whereyougo.R;
import menion.android.whereyougo.settings.Loc;
import android.location.Location;

/**
 * @author menion
 * @since 25.1.2010 2010
 */
public class Waypoint {

	// minimum ID for autogenerated ID
	public static final long MIN_GENERATED_ID = 1000000000;
	
	/** unique id */
	public long id;
	/** name of object */
	protected String name;
	/** location of this waypoint */
	protected Location location;
	/** additional database object */
	public Object object;
	
	/** temp variable for sorting */
	public int dist;

	public Waypoint(String name) {
    	this.id = 0;
        this.name = name == null ? Loc.get(R.string.point) : name;
	}
	
	public Waypoint(Location loc) {
		this(loc.getProvider());
		loc.setTime(System.currentTimeMillis());
		setLocation(loc, true);
	}
	
	public String getName() {
		return name;
	}
	
	/** return location in geodetic coordinates */
	public Location getLocation() {
		return location;
	}
	
	// max ID = 9223372036854775807
	public void setLocation(Location location, boolean initId) {
		this.location = location;
		if (initId) {
			id = generateId(location.getLatitude(), location.getLongitude(), name);
		}
	}
	
	public static long generateId(double lat, double lon, String name) {
		long id= (long) (MIN_GENERATED_ID + Math.abs(lat * 1000000 + lon * 1000)) + Math.abs(name.hashCode());
		while (id < MIN_GENERATED_ID)
			id += MIN_GENERATED_ID;
		return id;
	}
//	
//	public static void sortByDist(ArrayList<Waypoint> waypoints, final Location center) {
//		for (Waypoint wpt : waypoints) {
//			// clear distance before sorting
//			wpt.dist = -1;
//		}
//
//		Collections.sort(waypoints, new Comparator<Waypoint>() {
//			public int compare(Waypoint object1, Waypoint object2) {
//				if (object1.dist == -1) {
//					object1.dist = (int) center.distanceTo(object1.getLocation());
//				}
//				
//				if (object2.dist == -1) {
//					object2.dist = (int) center.distanceTo(object2.getLocation());
//				}
//				
//				return (int) (object1.dist - object2.dist);
//			}
//		});
//		
//		// no dist if only one item
//		if (waypoints.size() == 1) {
//			waypoints.get(0).dist = (int) center.distanceTo(waypoints.get(0).getLocation());
//		}
//	}
}
