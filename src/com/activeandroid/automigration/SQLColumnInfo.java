package com.activeandroid.automigration;

import android.text.TextUtils;

import com.activeandroid.util.SQLiteUtils.SQLiteType;

import java.util.ArrayList;
import java.util.Locale;

public class SQLColumnInfo {

	private String mColumnDefinition;
	private String mName;
	private SQLiteType mType;

	public SQLColumnInfo(String columnDefinition) {
		ArrayList<String> tokens = new ArrayList<String>();
		for (String token : columnDefinition.split(" ")) {
			if (TextUtils.isEmpty(token) == false)
				tokens.add(token);
		}

		if (tokens.size() < 2)
			throw new IllegalArgumentException("Failed to parse '" + columnDefinition + "' as sql column definition");
		
		this.mColumnDefinition = TextUtils.join(" ", tokens.subList(1, tokens.size()));

		this.mName = tokens.get(0);

		String tokenString = tokens.get(1).toUpperCase(Locale.US);
		if (tokenString.equals("VARCHAR")) {
			this.mType = SQLiteType.TEXT;
		} else if (tokenString.equals("BOOLEAN")) {
			this.mType = SQLiteType.INTEGER;
		} else {
			this.mType = SQLiteType.valueOf(tokenString);
		}

	}

	public String getName() {
		return mName;
	}

	public SQLiteType getType() {
		return mType;
	}
	
	public String getColumnDefinition() {
		return mName + " " + mColumnDefinition;
	}
	
	public boolean isPrimaryKey() {
		return mColumnDefinition.toUpperCase(Locale.US).contains("PRIMARY KEY");
	}
	
	public boolean isUnique() {
		return mColumnDefinition.toUpperCase(Locale.US).contains("UNIQUE");
	}
	
}
