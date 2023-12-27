/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE SENSOR_OFFSETS PACKING
package com.MAVLink.ardupilotmega;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
* Offsets and calibrations values for hardware sensors. This makes it easier to debug the calibration process.
*/
public class msg_sensor_offsets extends MAVLinkMessage{

    public static final int MAVLINK_MSG_ID_SENSOR_OFFSETS = 150;
    public static final int MAVLINK_MSG_ID_SENSOR_OFFSETS_CRC = 134;
    public static final int MAVLINK_MSG_LENGTH = 42;
    private static final long serialVersionUID = MAVLINK_MSG_ID_SENSOR_OFFSETS;


      
    /**
    * Magnetic declination.
    */
    public float mag_declination;
      
    /**
    * Raw pressure from barometer.
    */
    public int raw_press;
      
    /**
    * Raw temperature from barometer.
    */
    public int raw_temp;
      
    /**
    * Gyro X calibration.
    */
    public float gyro_cal_x;
      
    /**
    * Gyro Y calibration.
    */
    public float gyro_cal_y;
      
    /**
    * Gyro Z calibration.
    */
    public float gyro_cal_z;
      
    /**
    * Accel X calibration.
    */
    public float accel_cal_x;
      
    /**
    * Accel Y calibration.
    */
    public float accel_cal_y;
      
    /**
    * Accel Z calibration.
    */
    public float accel_cal_z;
      
    /**
    * Magnetometer X offset.
    */
    public short mag_ofs_x;
      
    /**
    * Magnetometer Y offset.
    */
    public short mag_ofs_y;
      
    /**
    * Magnetometer Z offset.
    */
    public short mag_ofs_z;
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_SENSOR_OFFSETS;
        packet.crc_extra = MAVLINK_MSG_ID_SENSOR_OFFSETS_CRC;
              
        packet.payload.putFloat(mag_declination);
              
        packet.payload.putInt(raw_press);
              
        packet.payload.putInt(raw_temp);
              
        packet.payload.putFloat(gyro_cal_x);
              
        packet.payload.putFloat(gyro_cal_y);
              
        packet.payload.putFloat(gyro_cal_z);
              
        packet.payload.putFloat(accel_cal_x);
              
        packet.payload.putFloat(accel_cal_y);
              
        packet.payload.putFloat(accel_cal_z);
              
        packet.payload.putShort(mag_ofs_x);
              
        packet.payload.putShort(mag_ofs_y);
              
        packet.payload.putShort(mag_ofs_z);
        
        return packet;
    }

    /**
    * Decode a sensor_offsets message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
              
        this.mag_declination = payload.getFloat();
              
        this.raw_press = payload.getInt();
              
        this.raw_temp = payload.getInt();
              
        this.gyro_cal_x = payload.getFloat();
              
        this.gyro_cal_y = payload.getFloat();
              
        this.gyro_cal_z = payload.getFloat();
              
        this.accel_cal_x = payload.getFloat();
              
        this.accel_cal_y = payload.getFloat();
              
        this.accel_cal_z = payload.getFloat();
              
        this.mag_ofs_x = payload.getShort();
              
        this.mag_ofs_y = payload.getShort();
              
        this.mag_ofs_z = payload.getShort();
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_sensor_offsets(){
        msgid = MAVLINK_MSG_ID_SENSOR_OFFSETS;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_sensor_offsets(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_SENSOR_OFFSETS;
        unpack(mavLinkPacket.payload);
    }

                            
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_SENSOR_OFFSETS - sysid:"+sysid+" compid:"+compid+" mag_declination:"+mag_declination+" raw_press:"+raw_press+" raw_temp:"+raw_temp+" gyro_cal_x:"+gyro_cal_x+" gyro_cal_y:"+gyro_cal_y+" gyro_cal_z:"+gyro_cal_z+" accel_cal_x:"+accel_cal_x+" accel_cal_y:"+accel_cal_y+" accel_cal_z:"+accel_cal_z+" mag_ofs_x:"+mag_ofs_x+" mag_ofs_y:"+mag_ofs_y+" mag_ofs_z:"+mag_ofs_z+"";
    }
}
        