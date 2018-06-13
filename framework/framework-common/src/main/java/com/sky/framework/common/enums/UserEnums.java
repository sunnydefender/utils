package com.sky.framework.common.enums;

import com.sky.framework.common.mybatis.IntegerValuedEnum;

public class UserEnums {
	public static enum UsedStatus implements IntegerValuedEnum {
		Enable(1),
		Disable(2);
		
		private int value;
		
		private UsedStatus(int value) {
			this.value = value;
		}
		
		@Override
		public int getValue() {	
			return value;
		}
	}
	
	public static enum UserStatus implements IntegerValuedEnum {
		Normal(1),
		Locked(2),
		Deleted(3),
		Black(4)
		;
		
		private int value;
		
		private UserStatus(int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return value;
		}
	}
	
	public static enum IdCardType implements IntegerValuedEnum {
		Unknown(0),
		ResidentIdCardOfMainlandChina(1)
		;

		private int value;
		
		private IdCardType(int value) {
			this.value = value;
		}
		
		@Override
		public int getValue() {
			return value;
		}
		
	}
	
	public static enum IdCardStatus implements IntegerValuedEnum {
		NotRealName(1),
		RealName(2)
		;

		private int value;

		private IdCardStatus(int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return value;
		}
		
	}
	
	public static enum Gender implements IntegerValuedEnum {
		Unknown(0),
		Femail(1),
		Mail(2)		
		;

		private int value;

		private Gender(int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return value;
		}
	}
	
	public static enum Age implements IntegerValuedEnum {
		Unknown(0)
		;

		private int value;

		private Age(int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return value;
		}
	}
	
	public static enum MarriageStatus implements IntegerValuedEnum {
		Unknown(0),
		NotMarried(1),
		Married(2),
		Secret(3)
		;

		private int value;

		private MarriageStatus(int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return value;
		}
	}

	public static enum SecurityLevel implements IntegerValuedEnum {
		Unknown(0)
		;

		private int value;

		private SecurityLevel(int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return value;
		}
	}

	public static enum SignUpAppType implements IntegerValuedEnum {
		Unknown(0),
		Web(1),
		IOS(2),
		Andriod(3)
		;

		private int value;

		private SignUpAppType(int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return value;
		}
	}

}
