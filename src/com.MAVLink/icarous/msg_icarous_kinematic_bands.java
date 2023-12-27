/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE ICAROUS_KINEMATIC_BANDS PACKING
package com.MAVLink.icarous;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
* Kinematic multi bands (track) output from Daidalus
*/
public class msg_icarous_kinematic_bands extends MAVLinkMessage{

    public static final int MAVLINK_MSG_ID_ICAROUS_KINEMATIC_BANDS = 42001;
    public static final int MAVLINK_MSG_ID_ICAROUS_KINEMATIC_BANDS_CRC = 239;
    public static final int MAVLINK_MSG_LENGTH = 46;
    private static final long serialVersionUID = MAVLINK_MSG_ID_ICAROUS_KINEMATIC_BANDS;


      
    /**
    * min angle (degrees)
    */
    public float min1;
      
    /**
    * max angle (degrees)
    */
    public float max1;
      
    /**
    * min angle (degrees)
    */
    public float min2;
      
    /**
    * max angle (degrees)
    */
    public float max2;
      
    /**
    * min angle (degrees)
    */
    public float min3;
      
    /**
    * max angle (degrees)
    */
    public float max3;
      
    /**
    * min angle (degrees)
    */
    public float min4;
      
    /**
    * max angle (degrees)
    */
    public float max4;
      
    /**
    * min angle (degrees)
    */
    public float min5;
      
    /**
    * max angle (degrees)
    */
    public float max5;
      
    /**
    * Number of track bands
    */
    public byte numBands;
      
    /**
    * See the TRACK_BAND_TYPES enum.
    */
    public short type1;
      
    /**
    * See the TRACK_BAND_TYPES enum.
    */
    public short type2;
      
    /**
    * See the TRACK_BAND_TYPES enum.
    */
    public short type3;
      
    /**
    * See the TRACK_BAND_TYPES enum.
    */
    public short type4;
      
    /**
    * See the TRACK_BAND_TYPES enum.
    */
    public short type5;
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_ICAROUS_KINEMATIC_BANDS;
        packet.crc_extra = MAVLINK_MSG_ID_ICAROUS_KINEMATIC_BANDS_CRC;
              
        packet.payload.putFloat(min1);
              
        packet.payload.putFloat(max1);
              
        packet.payload.putFloat(min2);
              
        packet.payload.putFloat(max2);
              
        packet.payload.putFloat(min3);
              
        packet.payload.putFloat(max3);
              
        packet.payload.putFloat(min4);
              
        packet.payload.putFloat(max4);
              
        packet.payload.putFloat(min5);
              
        packet.payload.putFloat(max5);
              
        packet.payload.putByte(numBands);
              
        packet.payload.putUnsignedByte(type1);
              
        packet.payload.putUnsignedByte(type2);
              
        packet.payload.putUnsignedByte(type3);
              
        packet.payload.putUnsignedByte(type4);
              
        packet.payload.putUnsignedByte(type5);
        
        return packet;
    }

    /**
    * Decode a icarous_kinematic_bands message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
              
        this.min1 = payload.getFloat();
              
        this.max1 = payload.getFloat();
              
        this.min2 = payload.getFloat();
              
        this.max2 = payload.getFloat();
              
        this.min3 = payload.getFloat();
              
        this.max3 = payload.getFloat();
              
        this.min4 = payload.getFloat();
              
        this.max4 = payload.getFloat();
              
        this.min5 = payload.getFloat();
              
        this.max5 = payload.getFloat();
              
        this.numBands = payload.getByte();
              
        this.type1 = payload.getUnsignedByte();
              
        this.type2 = payload.getUnsignedByte();
              
        this.type3 = payload.getUnsignedByte();
              
        this.type4 = payload.getUnsignedByte();
              
        this.type5 = payload.getUnsignedByte();
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_icarous_kinematic_bands(){
        msgid = MAVLINK_MSG_ID_ICAROUS_KINEMATIC_BANDS;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_icarous_kinematic_bands(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_ICAROUS_KINEMATIC_BANDS;
        unpack(mavLinkPacket.payload);
    }

                                    
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_ICAROUS_KINEMATIC_BANDS - sysid:"+sysid+" compid:"+compid+" min1:"+min1+" max1:"+max1+" min2:"+min2+" max2:"+max2+" min3:"+min3+" max3:"+max3+" min4:"+min4+" max4:"+max4+" min5:"+min5+" max5:"+max5+" numBands:"+numBands+" type1:"+type1+" type2:"+type2+" type3:"+type3+" type4:"+type4+" type5:"+type5+"";
    }
}
        