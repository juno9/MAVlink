/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE HIL_STATE PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
* Sent from simulation to autopilot. This packet is useful for high throughput applications such as hardware in the loop simulations.
*/
public class msg_hil_state extends MAVLinkMessage{

    public static final int MAVLINK_MSG_ID_HIL_STATE = 90;
    public static final int MAVLINK_MSG_ID_HIL_STATE_CRC = 183;
    public static final int MAVLINK_MSG_LENGTH = 56;
    private static final long serialVersionUID = MAVLINK_MSG_ID_HIL_STATE;


      
    /**
    * Timestamp (UNIX Epoch time or time since system boot). The receiving end can infer timestamp format (since 1.1.1970 or since system boot) by checking for the magnitude the number.
    */
    public long time_usec;
      
    /**
    * Roll angle
    */
    public float roll;
      
    /**
    * Pitch angle
    */
    public float pitch;
      
    /**
    * Yaw angle
    */
    public float yaw;
      
    /**
    * Body frame roll / phi angular speed
    */
    public float rollspeed;
      
    /**
    * Body frame pitch / theta angular speed
    */
    public float pitchspeed;
      
    /**
    * Body frame yaw / psi angular speed
    */
    public float yawspeed;
      
    /**
    * Latitude
    */
    public int lat;
      
    /**
    * Longitude
    */
    public int lon;
      
    /**
    * Altitude
    */
    public int alt;
      
    /**
    * Ground X Speed (Latitude)
    */
    public short vx;
      
    /**
    * Ground Y Speed (Longitude)
    */
    public short vy;
      
    /**
    * Ground Z Speed (Altitude)
    */
    public short vz;
      
    /**
    * X acceleration
    */
    public short xacc;
      
    /**
    * Y acceleration
    */
    public short yacc;
      
    /**
    * Z acceleration
    */
    public short zacc;
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_HIL_STATE;
        packet.crc_extra = MAVLINK_MSG_ID_HIL_STATE_CRC;
              
        packet.payload.putUnsignedLong(time_usec);
              
        packet.payload.putFloat(roll);
              
        packet.payload.putFloat(pitch);
              
        packet.payload.putFloat(yaw);
              
        packet.payload.putFloat(rollspeed);
              
        packet.payload.putFloat(pitchspeed);
              
        packet.payload.putFloat(yawspeed);
              
        packet.payload.putInt(lat);
              
        packet.payload.putInt(lon);
              
        packet.payload.putInt(alt);
              
        packet.payload.putShort(vx);
              
        packet.payload.putShort(vy);
              
        packet.payload.putShort(vz);
              
        packet.payload.putShort(xacc);
              
        packet.payload.putShort(yacc);
              
        packet.payload.putShort(zacc);
        
        return packet;
    }

    /**
    * Decode a hil_state message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
              
        this.time_usec = payload.getUnsignedLong();
              
        this.roll = payload.getFloat();
              
        this.pitch = payload.getFloat();
              
        this.yaw = payload.getFloat();
              
        this.rollspeed = payload.getFloat();
              
        this.pitchspeed = payload.getFloat();
              
        this.yawspeed = payload.getFloat();
              
        this.lat = payload.getInt();
              
        this.lon = payload.getInt();
              
        this.alt = payload.getInt();
              
        this.vx = payload.getShort();
              
        this.vy = payload.getShort();
              
        this.vz = payload.getShort();
              
        this.xacc = payload.getShort();
              
        this.yacc = payload.getShort();
              
        this.zacc = payload.getShort();
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_hil_state(){
        msgid = MAVLINK_MSG_ID_HIL_STATE;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_hil_state(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_HIL_STATE;
        unpack(mavLinkPacket.payload);
    }

                                    
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_HIL_STATE - sysid:"+sysid+" compid:"+compid+" time_usec:"+time_usec+" roll:"+roll+" pitch:"+pitch+" yaw:"+yaw+" rollspeed:"+rollspeed+" pitchspeed:"+pitchspeed+" yawspeed:"+yawspeed+" lat:"+lat+" lon:"+lon+" alt:"+alt+" vx:"+vx+" vy:"+vy+" vz:"+vz+" xacc:"+xacc+" yacc:"+yacc+" zacc:"+zacc+"";
    }
}
        