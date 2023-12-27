/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE RALLY_FETCH_POINT PACKING
package com.MAVLink.ardupilotmega;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
* Request a current rally point from MAV. MAV should respond with a RALLY_POINT message. MAV should not respond if the request is invalid.
*/
public class msg_rally_fetch_point extends MAVLinkMessage{

    public static final int MAVLINK_MSG_ID_RALLY_FETCH_POINT = 176;
    public static final int MAVLINK_MSG_ID_RALLY_FETCH_POINT_CRC = 234;
    public static final int MAVLINK_MSG_LENGTH = 3;
    private static final long serialVersionUID = MAVLINK_MSG_ID_RALLY_FETCH_POINT;


      
    /**
    * System ID.
    */
    public short target_system;
      
    /**
    * Component ID.
    */
    public short target_component;
      
    /**
    * Point index (first point is 0).
    */
    public short idx;
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_RALLY_FETCH_POINT;
        packet.crc_extra = MAVLINK_MSG_ID_RALLY_FETCH_POINT_CRC;
              
        packet.payload.putUnsignedByte(target_system);
              
        packet.payload.putUnsignedByte(target_component);
              
        packet.payload.putUnsignedByte(idx);
        
        return packet;
    }

    /**
    * Decode a rally_fetch_point message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
              
        this.target_system = payload.getUnsignedByte();
              
        this.target_component = payload.getUnsignedByte();
              
        this.idx = payload.getUnsignedByte();
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_rally_fetch_point(){
        msgid = MAVLINK_MSG_ID_RALLY_FETCH_POINT;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_rally_fetch_point(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_RALLY_FETCH_POINT;
        unpack(mavLinkPacket.payload);
    }

          
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_RALLY_FETCH_POINT - sysid:"+sysid+" compid:"+compid+" target_system:"+target_system+" target_component:"+target_component+" idx:"+idx+"";
    }
}
        