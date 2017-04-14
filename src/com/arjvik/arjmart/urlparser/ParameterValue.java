package com.arjvik.arjmart.urlparser;

import com.arjvik.arjmart.urlparser.ParameterType;

public class ParameterValue {
		ParameterType type;
		String value;
		
		public ParameterValue(ParameterType type, String value) {
			super();
			this.type = type;
			this.value = value;
		}
		
		public ParameterType getType() {
			return type;
		}
		public void setType(ParameterType type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}