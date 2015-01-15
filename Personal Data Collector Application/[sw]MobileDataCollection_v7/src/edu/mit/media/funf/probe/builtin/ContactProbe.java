/**
 * 
 * Funf: Open Sensing Framework
 * Copyright (C) 2010-2011 Nadav Aharony, Wei Pan, Alex Pentland.
 * Acknowledgments: Alan Gardner
 * Contact: nadav@media.mit.edu
 * 
 * This file is part of Funf.
 * 
 * Funf is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * Funf is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Funf. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.mit.media.funf.probe.builtin;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.mit.media.funf.Schedule;
import edu.mit.media.funf.probe.Probe.ContinuableProbe;
import edu.mit.media.funf.probe.Probe.DisplayName;
import edu.mit.media.funf.probe.Probe.RequiredPermissions;
import edu.mit.media.funf.probe.builtin.ProbeKeys.ContactKeys;

@DisplayName("Contacts Probe")
@RequiredPermissions(android.Manifest.permission.READ_CONTACTS)
@Schedule.DefaultSchedule(interval=604800)
public class ContactProbe extends ContentProviderProbe implements ContactKeys, ContinuableProbe {

	private class VersionMap extends TreeMap<Integer,Integer> {private static final long serialVersionUID = 8835219249716647742L;}
	
	private VersionMap dataIdToVersion = new VersionMap();
	
	@Override
	protected Cursor getCursor(String[] projection) {
		return getContext().getContentResolver().query(
				ContactsContract.Data.CONTENT_URI,
                projection, 
                null, //Data.MIMETYPE + " IN ('" + Email.CONTENT_ITEM_TYPE + "')",
                null,//new String[] {"('" + Utils.join(Arrays.asList(Email.MIMETYPE, Event.MIMETYPE), "','") +"')"}, 
                Data.CONTACT_ID + " ASC");
	}
	
	
	private class ContactDataCell extends CursorCell<Object> {
		
		private Map<String,CursorCell<?>> cursorCells;
		
		ContactDataCell() {
			cursorCells = new HashMap<String, CursorCell<?>>();
			
			// Email
			cursorCells.put(getKey(Email.CONTENT_ITEM_TYPE, Data.DATA1), stringCell());
			cursorCells.put(getKey(Email.CONTENT_ITEM_TYPE, Email.TYPE), intCell());
			cursorCells.put(getKey(Email.CONTENT_ITEM_TYPE, Email.LABEL), stringCell());
			cursorCells.put(getKey(Email.CONTENT_ITEM_TYPE, Email.DISPLAY_NAME), stringCell());
			
			// Event
			cursorCells.put(getKey(Event.CONTENT_ITEM_TYPE, Event.START_DATE), stringCell());
			cursorCells.put(getKey(Event.CONTENT_ITEM_TYPE, Event.TYPE), intCell());
			cursorCells.put(getKey(Event.CONTENT_ITEM_TYPE, Event.LABEL), stringCell());
			
			// Group Membership
			cursorCells.put(getKey(Event.CONTENT_ITEM_TYPE, GroupMembership.GROUP_ROW_ID), longCell());
			
			// IM Address
			cursorCells.put(getKey(Im.CONTENT_ITEM_TYPE, Im.DATA), stringCell());
			cursorCells.put(getKey(Im.CONTENT_ITEM_TYPE, Im.TYPE), intCell());
			cursorCells.put(getKey(Im.CONTENT_ITEM_TYPE, Im.LABEL), stringCell());
			cursorCells.put(getKey(Im.CONTENT_ITEM_TYPE, Im.PROTOCOL), stringCell());
			cursorCells.put(getKey(Im.CONTENT_ITEM_TYPE, Im.CUSTOM_PROTOCOL), stringCell());
			
			// Nickname
			cursorCells.put(getKey(Nickname.CONTENT_ITEM_TYPE, Nickname.NAME), stringCell());
			cursorCells.put(getKey(Nickname.CONTENT_ITEM_TYPE, Nickname.TYPE), intCell());
			cursorCells.put(getKey(Nickname.CONTENT_ITEM_TYPE, Nickname.LABEL), stringCell());
			
			// Note
			cursorCells.put(getKey(Note.CONTENT_ITEM_TYPE, Note.NOTE), stringCell());
			// TODO: do we need notes?
			
			// Organization
			cursorCells.put(getKey(Organization.CONTENT_ITEM_TYPE, Organization.COMPANY), stringCell());
			cursorCells.put(getKey(Organization.CONTENT_ITEM_TYPE, Organization.TYPE), intCell());
			cursorCells.put(getKey(Organization.CONTENT_ITEM_TYPE, Organization.LABEL), stringCell());
			cursorCells.put(getKey(Organization.CONTENT_ITEM_TYPE, Organization.TITLE), stringCell());
			cursorCells.put(getKey(Organization.CONTENT_ITEM_TYPE, Organization.DEPARTMENT), stringCell());
			cursorCells.put(getKey(Organization.CONTENT_ITEM_TYPE, Organization.JOB_DESCRIPTION), stringCell());
			cursorCells.put(getKey(Organization.CONTENT_ITEM_TYPE, Organization.SYMBOL), stringCell());
			cursorCells.put(getKey(Organization.CONTENT_ITEM_TYPE, Organization.PHONETIC_NAME), stringCell());
			cursorCells.put(getKey(Organization.CONTENT_ITEM_TYPE, Organization.OFFICE_LOCATION), stringCell());
			cursorCells.put(getKey(Organization.CONTENT_ITEM_TYPE, Data.DATA10), stringCell()); // Phonetic_Name_Style
			
			// Phone
			cursorCells.put(getKey(Phone.CONTENT_ITEM_TYPE, Phone.NUMBER), new PhoneNumberCell());
			cursorCells.put(getKey(Phone.CONTENT_ITEM_TYPE, Phone.TYPE), intCell());
			cursorCells.put(getKey(Phone.CONTENT_ITEM_TYPE, Phone.LABEL), stringCell());
			
			// Photo (Skipped)
			cursorCells.put(getKey(Photo.CONTENT_ITEM_TYPE, Photo.PHOTO), new NullCell());
			
			// Relation
			cursorCells.put(getKey(Relation.CONTENT_ITEM_TYPE, Relation.NAME), stringCell());
			cursorCells.put(getKey(Relation.CONTENT_ITEM_TYPE, Relation.TYPE), intCell());
			cursorCells.put(getKey(Relation.CONTENT_ITEM_TYPE, Relation.LABEL), stringCell());
			
			// SipAddress (API 9) Used defaults
			
			// StructuredName
			cursorCells.put(getKey(StructuredName.CONTENT_ITEM_TYPE, StructuredName.DISPLAY_NAME), stringCell());
			cursorCells.put(getKey(StructuredName.CONTENT_ITEM_TYPE, StructuredName.GIVEN_NAME), stringCell());
			cursorCells.put(getKey(StructuredName.CONTENT_ITEM_TYPE, StructuredName.FAMILY_NAME), stringCell());
			cursorCells.put(getKey(StructuredName.CONTENT_ITEM_TYPE, StructuredName.PREFIX), stringCell());
			cursorCells.put(getKey(StructuredName.CONTENT_ITEM_TYPE, StructuredName.MIDDLE_NAME), stringCell());
			cursorCells.put(getKey(StructuredName.CONTENT_ITEM_TYPE, StructuredName.SUFFIX), stringCell());
			cursorCells.put(getKey(StructuredName.CONTENT_ITEM_TYPE, StructuredName.PHONETIC_GIVEN_NAME), stringCell());
			cursorCells.put(getKey(StructuredName.CONTENT_ITEM_TYPE, StructuredName.PHONETIC_MIDDLE_NAME), stringCell());
			cursorCells.put(getKey(StructuredName.CONTENT_ITEM_TYPE, StructuredName.PHONETIC_FAMILY_NAME), stringCell());
			
			// Structured Postal
			cursorCells.put(getKey(StructuredPostal.CONTENT_ITEM_TYPE, StructuredPostal.FORMATTED_ADDRESS), stringCell());
			cursorCells.put(getKey(StructuredPostal.CONTENT_ITEM_TYPE, StructuredPostal.TYPE), intCell());
			cursorCells.put(getKey(StructuredPostal.CONTENT_ITEM_TYPE, StructuredPostal.LABEL), stringCell());
			cursorCells.put(getKey(StructuredPostal.CONTENT_ITEM_TYPE, StructuredPostal.STREET), stringCell());
			cursorCells.put(getKey(StructuredPostal.CONTENT_ITEM_TYPE, StructuredPostal.POBOX), stringCell());
			cursorCells.put(getKey(StructuredPostal.CONTENT_ITEM_TYPE, StructuredPostal.NEIGHBORHOOD), stringCell());
			cursorCells.put(getKey(StructuredPostal.CONTENT_ITEM_TYPE, StructuredPostal.CITY), stringCell());
			cursorCells.put(getKey(StructuredPostal.CONTENT_ITEM_TYPE, StructuredPostal.REGION), stringCell());
			cursorCells.put(getKey(StructuredPostal.CONTENT_ITEM_TYPE, StructuredPostal.POSTCODE), stringCell());
			cursorCells.put(getKey(StructuredPostal.CONTENT_ITEM_TYPE, StructuredPostal.COUNTRY), stringCell());
			
			// Website
			cursorCells.put(getKey(Website.CONTENT_ITEM_TYPE, Website.URL), stringCell());
			cursorCells.put(getKey(Website.CONTENT_ITEM_TYPE, Website.TYPE), intCell());
			cursorCells.put(getKey(Website.CONTENT_ITEM_TYPE, Website.LABEL), stringCell());
			
		}
		
		private String getKey(String mimeType, String columnName) {
			return mimeType + "__" + columnName;
		}
		
		@Override
		public Object getData(Cursor cursor, int columnIndex) {
			String mimeType = stringCell().getData(cursor,Data.MIMETYPE);
			String columnName = cursor.getColumnName(columnIndex);
			CursorCell<?> cursorCell = cursorCells.get(getKey(mimeType, columnName));
			if (cursorCell == null) {
				cursorCell = anyCell(); // Default
			}
			return cursorCell.getData(cursor, columnIndex);
		}	
	}
	
	private ContactDataCell contactDataCell;
	protected ContactDataCell contactDataCell() {
		if (contactDataCell == null) {
			contactDataCell = new ContactDataCell();
		}
		return contactDataCell;
	}
	
	private Map<String, CursorCell<?>> dataProjectionMap;
	private Map<String, CursorCell<?>> getDataProjectionMap() {
		if (dataProjectionMap == null) {
			Map<String,CursorCell<?>> projectionKeyToType = new HashMap<String, CursorCell<?>>();
			projectionKeyToType.put(Data._ID, intCell());
			projectionKeyToType.put(Data.RAW_CONTACT_ID, longCell());
			projectionKeyToType.put(Data.MIMETYPE, stringCell());
			projectionKeyToType.put(Data.IS_PRIMARY, intCell());
			projectionKeyToType.put(Data.IS_SUPER_PRIMARY, intCell());
			projectionKeyToType.put(Data.DATA_VERSION, intCell());
			projectionKeyToType.put(Data.DATA1, contactDataCell());
			projectionKeyToType.put(Data.DATA2, contactDataCell());
			projectionKeyToType.put(Data.DATA3, contactDataCell());
			projectionKeyToType.put(Data.DATA4, contactDataCell());
			projectionKeyToType.put(Data.DATA5, contactDataCell());
			projectionKeyToType.put(Data.DATA6, contactDataCell());
			projectionKeyToType.put(Data.DATA7, contactDataCell());
			projectionKeyToType.put(Data.DATA8, contactDataCell());
			projectionKeyToType.put(Data.DATA9, contactDataCell());
			projectionKeyToType.put(Data.DATA10, contactDataCell());
			projectionKeyToType.put(Data.DATA11, contactDataCell());
			projectionKeyToType.put(Data.DATA12, contactDataCell());
			projectionKeyToType.put(Data.DATA13, contactDataCell());
			projectionKeyToType.put(Data.DATA14, contactDataCell());
			projectionKeyToType.put(Data.DATA15, contactDataCell());
			dataProjectionMap = projectionKeyToType;
		}
		return dataProjectionMap;
	}
	
	private Map<String, CursorCell<?>> contactProjectionMap;
	private Map<String, CursorCell<?>> getContactProjectionMap() {
		if (contactProjectionMap == null) {
			Map<String,CursorCell<?>> projectionKeyToType = new HashMap<String, CursorCell<?>>();
			projectionKeyToType.put(Data.CONTACT_ID, longCell());
			//projectionKeyToType.put(Data.AGGREGATION_MODE, intCell());  Doesn't exist for some reason
			//projectionKeyToType.put(Data.DELETED, intCell());  Doesn't exist for some reason
			projectionKeyToType.put(Data.LOOKUP_KEY, stringCell());
			projectionKeyToType.put(Data.DISPLAY_NAME, stringCell());
			projectionKeyToType.put(Data.PHOTO_ID, longCell());
			projectionKeyToType.put(Data.IN_VISIBLE_GROUP, intCell());
			projectionKeyToType.put(Data.TIMES_CONTACTED, intCell());
			projectionKeyToType.put(Data.LAST_TIME_CONTACTED, intCell());
			projectionKeyToType.put(Data.STARRED, intCell());
			projectionKeyToType.put(Data.CUSTOM_RINGTONE, stringCell());
			projectionKeyToType.put(Data.SEND_TO_VOICEMAIL, intCell());
			contactProjectionMap = projectionKeyToType;
		}
		return contactProjectionMap;
	}
	
	
	@Override
	protected Map<String, CursorCell<?>> getProjectionMap() {
		Map<String,CursorCell<?>> projectionKeyToType = new HashMap<String, CursorCell<?>>();
		projectionKeyToType.putAll(getDataProjectionMap());
		projectionKeyToType.putAll(getContactProjectionMap());
		return projectionKeyToType;
	}
	
	@Override
	protected JsonObject parseData(Cursor cursor, String[] projection, Map<String, CursorCell<?>> projectionMap) {
		JsonObject contact = super.parseData(cursor, projection, getContactProjectionMap());
		
		JsonArray contactData = new JsonArray();
		long originalContactId = cursor.getLong(cursor.getColumnIndex(Data.CONTACT_ID));
		long contactId = originalContactId;
		boolean hasNext = true;
		boolean hasChanged = false;
		do {
			JsonObject contactDatum = super.parseData(cursor, projection, getDataProjectionMap());
			contactData.add(contactDatum);
			int id = contactDatum.get(Data._ID).getAsInt();
			int version = contactDatum.get(Data.DATA_VERSION).getAsInt();
			Integer oldVersion = dataIdToVersion.get(id);
			if (oldVersion == null || !oldVersion.equals(version)) {
				hasChanged = true;
				dataIdToVersion.put(id, version);
			}
			hasNext = cursor.moveToNext();
			if (hasNext) {
				contactId = cursor.getLong(cursor.getColumnIndex(Data.CONTACT_ID));
			}
		} while(hasNext && originalContactId == contactId);
		if (hasNext) {
			cursor.moveToPrevious();
		}
		contact.add(CONTACT_DATA, contactData);
		return hasChanged ? contact : null;
	}


	@Override
	public void setCheckpoint(JsonElement checkpoint) {
		dataIdToVersion = (checkpoint == null) ? new VersionMap() : getGson().fromJson(checkpoint, VersionMap.class);
	}

	@Override
	public JsonElement getCheckpoint() {
		return getGson().toJsonTree(dataIdToVersion);
	}

}
