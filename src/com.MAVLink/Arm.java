package com.MAVLink;


import com.MAVLink.common.msg_command_long;
import com.MAVLink.enums.MAV_CMD;

public class Arm implements FlightCommand {
	
	public Arm(int index, boolean arm) 
	{
		msg_command_long message = new msg_command_long();
		message.target_system = (short)index;
		//(short)(index+1);//1;
		message.target_component = 1;
		//(byte) MAV_COMPONENT.MAV_COMP_ID_SYSTEM_CONTROL;

		message.command = MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM;
		message.param1 = arm ? 1 : 0;
		message.param2 = 0;
		message.param3 = 0;
		message.param4 = 0;
		message.param5 = 0;
		message.param6 = 0;
		message.param7 = 0;
		message.confirmation = 0;
		packet  = message.pack();
		rawData = packet.encodePacket();
	}
	@Override
	public byte[] getRawData() { return this.rawData; }
	
	
	private MAVLinkPacket packet;
	private byte[] 		  rawData;
}
