package com.MAVLink;


import com.MAVLink.common.msg_command_long;
import com.MAVLink.enums.MAV_CMD;


public class Takeoff implements FlightCommand {
	
	public Takeoff(int index, int alt)
	{
		msg_command_long message = new msg_command_long();
		message.target_system = (short)index;//(short)(index+1); //1;
		message.target_component = 1; // 1; //200, 250, 0, MAV_COMPONENT.MAV_COMP_ID_IMU; // 1; //MAV_COMPONENT.MAV_COMP_ID_ALL;
		message.command = MAV_CMD.MAV_CMD_NAV_TAKEOFF;
		message.param1 = -1;  // px4 min pitch
		message.param2 = 0;
		message.param3 = 0;
		message.param4 = 0.0f / 0.0f;  // px4 heading
		message.param5 = 0.0f / 0.0f;  // px4 latitude
		message.param6 = 0.0f / 0.0f;  // px4 longitude
		message.param7 = alt;/*.valueInMeters();*/
		message.confirmation = 1;
		packet  = message.pack();
		
//		packet.setProtocol(Protocol.PROTOCOL_1_0);
		
		rawData = packet.encodePacket();
	}
	
//	double minimumTakeoffAltitude(Drone drone) {
//		return 0;
//		// 연결 수립시, 전체 매개변수를 받아놓고 거기서 참조한다.
//		// 전체 매개변수는 드론객체의 파라메터 클래스에 있겠다.
//	    //QString takeoffAltParam("MIS_TAKEOFF_ALT");
//		// 가져와서 디폴트  alt같이나 gcs 디폴트 값으로 리턴.
//
////	    if (vehicle->parameterManager()->parameterExists(FactSystem::defaultComponentId, takeoffAltParam)) {
////	        return vehicle->parameterManager()->getParameter(FactSystem::defaultComponentId, takeoffAltParam)->rawValue().toDouble();
////	    } else {
////	        return FirmwarePlugin::minimumTakeoffAltitude(vehicle);
////	    }
//	}
//
//	public Takeoff(int index, Drone drone, int alt) {
//
//		// TODO For PX4, AMSL Altitude Compare. 2.6 default m
//		float rawAltAMSL = drone.getAllData().getRawGPS().getAltAsl();
//		float altAMSL = drone.getAllData().getGps().getAltAsl();
////		if(altAMSL == 0)
////			altAMSL = drone.getAllData().getGps().getAltAsl();
//
//		if(Double.isNaN(rawAltAMSL)) {
//			if(Double.isNaN(altAMSL)) {
//				rawAltAMSL = (float) 2.5;
//			} else
//				rawAltAMSL = altAMSL; //(float) 2.5;
//		}
//
//		// 2.5 is px4 default alt param7 range.
//		double targetAlt =  Math.max(alt, 2.5) + rawAltAMSL;
//
//		String firmwareType = drone.getAllData().getMyFirmwareType();
//		String vehicleType = drone.getAllData().getMyVehicleType();
//
//
//		int command = MAV_CMD.MAV_CMD_NAV_TAKEOFF;
////        if(vehicleType == "VTOL_DUOROTOR" ||
////        		vehicleType == "VTOL_QUADROTOR" ||
////        				vehicleType == "VTOL_TILTROTOR" ||
////        						vehicleType == "VTOL_RESERVED2" ||
////        								vehicleType == "VTOL_RESERVED3" ||
////        										vehicleType == "VTOL_RESERVED4" ||
////        												vehicleType == "VTOL_RESERVED5" ) {
////        	command = MAV_CMD.MAV_CMD_NAV_VTOL_TAKEOFF;
//////        	command = MAV_CMD.MAV_CMD_DO_VTOL_TRANSITION;
//////        	command = MAV_CMD.MAV_CMD_NAV_VTOL_LAND;
////        }
//
//
//		msg_command_long message = new msg_command_long();
//		message.target_system = (short)index;//(short)(index+1); //1;
//		message.target_component = 1; // 1; //200, 250, 0, MAV_COMPONENT.MAV_COMP_ID_IMU; // 1; //MAV_COMPONENT.MAV_COMP_ID_ALL;
//		message.command = command; // MAV_CMD.MAV_CMD_NAV_TAKEOFF;
//		message.param1 = -1;  // px4 min pitch
//		message.param2 = 0;
//		message.param3 = 0;
//		message.param4 = 0.0f / 0.0f;  // px4 heading
////		message.param5 = 0.0f / 0.0f;  // px4 latitude
////		message.param6 = 0.0f / 0.0f;  // px4 longitude
//		message.param5 = 0.0f / 0.0f; // (float) drone.getAllData().getGps().getLatitude();  // px4 latitude
//		message.param6 = 0.0f / 0.0f; // (float) drone.getAllData().getGps().getLongitude();  // px4 longitude
//		message.param7 = (float) targetAlt;/*.valueInMeters();*/
//		message.confirmation = 1;
//		packet  = message.pack();
//
////		packet.setProtocol(Protocol.PROTOCOL_1_0);
//
//		rawData = packet.encodePacket();
//	}
//
//	public Takeoff(int index, Drone drone, int alt, boolean v1) {
//		// TODO For PX4, AMSL Altitude Compare. 2.6 default m
//		float rawAltAMSL = drone.getAllData().getRawGPS().getAltAsl();
//		float altAMSL = drone.getAllData().getGps().getAltAsl();
////		if(altAMSL == 0)
////			altAMSL = drone.getAllData().getGps().getAltAsl();
//
//		if(Double.isNaN(rawAltAMSL)) {
//			if(Double.isNaN(altAMSL)) {
//				rawAltAMSL = (float) 2.5;
//			} else
//				rawAltAMSL = altAMSL; //(float) 2.5;
//		}
//
//		// 2.5 is px4 default alt param7 range.
//		double targetAlt =  Math.max(alt, 2.5) + rawAltAMSL;
//
//		String firmwareType = drone.getAllData().getMyFirmwareType();
//		String vehicleType = drone.getAllData().getMyVehicleType();
//
//
//		int command = MAV_CMD.MAV_CMD_NAV_TAKEOFF;
////        if(vehicleType == "VTOL_DUOROTOR" ||
////        		vehicleType == "VTOL_QUADROTOR" ||
////        				vehicleType == "VTOL_TILTROTOR" ||
////        						vehicleType == "VTOL_RESERVED2" ||
////        								vehicleType == "VTOL_RESERVED3" ||
////        										vehicleType == "VTOL_RESERVED4" ||
////        												vehicleType == "VTOL_RESERVED5" ) {
////        	command = MAV_CMD.MAV_CMD_NAV_VTOL_TAKEOFF;
//////        	command = MAV_CMD.MAV_CMD_DO_VTOL_TRANSITION;
//////        	command = MAV_CMD.MAV_CMD_NAV_VTOL_LAND;
////        }
//
//
//		msg_command_long message = new msg_command_long();
//		message.target_system = (short)index;//(short)(index+1); //1;
//		message.target_component = 1; // 1; //200, 250, 0, MAV_COMPONENT.MAV_COMP_ID_IMU; // 1; //MAV_COMPONENT.MAV_COMP_ID_ALL;
//		message.command = command;
//		message.param1 = -1;  // px4 min pitch
//		message.param2 = 0;
//		message.param3 = 0;
//		message.param4 = 0.0f / 0.0f;  // px4 heading
////		message.param5 = 0.0f / 0.0f;  // px4 latitude
////		message.param6 = 0.0f / 0.0f;  // px4 longitude
//		message.param5 = 0.0f / 0.0f; // (float) drone.getAllData().getGps().getLatitude();  // px4 latitude
//		message.param6 = 0.0f / 0.0f; // (float) drone.getAllData().getGps().getLongitude();  // px4 longitude
//		message.param7 = (float) targetAlt;/*.valueInMeters();*/
//		message.confirmation = 1;
//		packet  = message.pack();
//
//
//		if(v1)
//			packet.setProtocol(MAVLinkPacket.Protocol.PROTOCOL_1_0);
//
//		rawData = packet.encodePacket();
//	}
	
	@Override
	public byte[] getRawData() { return this.rawData; }
	
	
	private MAVLinkPacket packet;
	private byte[] 		  rawData;
}

// MAV_CMD_DO_REPOSITION   // goto position and oto altitude
