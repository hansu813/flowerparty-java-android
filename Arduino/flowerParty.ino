#include <Wire.h>

#define TB_REG_SENSOR  0x06
#define TB_REG_OBJECT  0x07
#define A0Pin 0
#define A1Pin 1
#define PWMpin 3 //PWM 제어를 위한 핀 3번으로 지정
#define DIRpin 4 //방향 제어를 위한 핀 4번으로 지정


int sensorVal = 0;
int jodo = 0;

uint8_t _deviceAddress = 0x3A;                // default Address
int16_t _rawObject;
int16_t _rawSensor;

const unsigned char crc8_table[256]=          //CRC table (Please don't change this value)
{
    0x00, 0x07, 0x0E, 0x09, 0x1C, 0x1B, 0x12, 0x15,    0x38, 0x3F, 0x36, 0x31, 0x24, 0x23, 0x2A, 0x2D,
    0x70, 0x77, 0x7E, 0x79, 0x6C, 0x6B, 0x62, 0x65,    0x48, 0x4F, 0x46, 0x41, 0x54, 0x53, 0x5A, 0x5D,
    0xE0, 0xE7, 0xEE, 0xE9, 0xFC, 0xFB, 0xF2, 0xF5,    0xD8, 0xDF, 0xD6, 0xD1, 0xC4, 0xC3, 0xCA, 0xCD,
    0x90, 0x97, 0x9E, 0x99, 0x8C, 0x8B, 0x82, 0x85,    0xA8, 0xAF, 0xA6, 0xA1, 0xB4, 0xB3, 0xBA, 0xBD,
    0xC7, 0xC0, 0xC9, 0xCE, 0xDB, 0xDC, 0xD5, 0xD2,    0xFF, 0xF8, 0xF1, 0xF6, 0xE3, 0xE4, 0xED, 0xEA,
    0xB7, 0xB0, 0xB9, 0xBE, 0xAB, 0xAC, 0xA5, 0xA2,    0x8F, 0x88, 0x81, 0x86, 0x93, 0x94, 0x9D, 0x9A,
    0x27, 0x20, 0x29, 0x2E, 0x3B, 0x3C, 0x35, 0x32,    0x1F, 0x18, 0x11, 0x16, 0x03, 0x04, 0x0D, 0x0A,
    0x57, 0x50, 0x59, 0x5E, 0x4B, 0x4C, 0x45, 0x42,    0x6F, 0x68, 0x61, 0x66, 0x73, 0x74, 0x7D, 0x7A,
    0x89, 0x8E, 0x87, 0x80, 0x95, 0x92, 0x9B, 0x9C,    0xB1, 0xB6, 0xBF, 0xB8, 0xAD, 0xAA, 0xA3, 0xA4,
    0xF9, 0xFE, 0xF7, 0xF0, 0xE5, 0xE2, 0xEB, 0xEC,    0xC1, 0xC6, 0xCF, 0xC8, 0xDD, 0xDA, 0xD3, 0xD4,
    0x69, 0x6E, 0x67, 0x60, 0x75, 0x72, 0x7B, 0x7C,    0x51, 0x56, 0x5F, 0x58, 0x4D, 0x4A, 0x43, 0x44,
    0x19, 0x1E, 0x17, 0x10, 0x05, 0x02, 0x0B, 0x0C,    0x21, 0x26, 0x2F, 0x28, 0x3D, 0x3A, 0x33, 0x34,
    0x4E, 0x49, 0x40, 0x47, 0x52, 0x55, 0x5C, 0x5B,    0x76, 0x71, 0x78, 0x7F, 0x6A, 0x6D, 0x64, 0x63,
    0x3E, 0x39, 0x30, 0x37, 0x22, 0x25, 0x2C, 0x2B,    0x06, 0x01, 0x08, 0x0F, 0x1A, 0x1D, 0x14, 0x13,
    0xAE, 0xA9, 0xA0, 0xA7, 0xB2, 0xB5, 0xBC, 0xBB,    0x96, 0x91, 0x98, 0x9F, 0x8A, 0x8D, 0x84, 0x83,
    0xDE, 0xD9, 0xD0, 0xD7, 0xC2, 0xC5, 0xCC, 0xCB,    0xE6, 0xE1, 0xE8, 0xEF, 0xFA, 0xFD, 0xF4, 0xF3  
};

void setup( )  {
  pinMode(DIRpin, OUTPUT);
  Serial.begin(9600);

  Wire.begin();                              // Initialize I2C
  delay(1000);                                // Waiting for sensor initialization.(min : 200ms)
  Serial.begin(9600);                       // Initialize Serial to log output
  while (!Serial) ;
               //8번 핀으로 데이터를 값을 받습니다.
}
void loop() {
    jodo = analogRead(A1Pin);
    delay(500);
    Serial.print("조도 = ");
    Serial.println(jodo);
  
    sensorVal = analogRead(A0Pin);  // 토양센서 센서값 읽어 저장
    delay(500);
    Serial.print("습도 = ");
    Serial.println(sensorVal);  // 0(습함) ~ 1023(건조)값 출력 

   // 습도 값에 따라 출력 처리 다르게 해줌

  if ( sensorVal <= 450) {    

    Serial.println(" 매우 습함 ! ");        

  }

  if (sensorVal > 500 && sensorVal <= 750) {

    Serial.println(" 양호 ! ");    

  }

  else if ( sensorVal > 750){

    Serial.println(" 매우 건조 ! ");    
    Motor(HIGH,150);//150의 출력으로 정방향 회전
    delay(3000);    //3000ms 즉 3초간 대기
    Motor(HIGH,255);//255(최대)의 출력으로 정방향 회전
    delay(3000);
    Motor(HIGH,0);//정지
    delay(3000);
  }
  {
    pinMode(DIRpin, OUTPUT);
  }
  
 if(GetObject() && GetSensor())              // 대상온도 및 센서온도 Read 완료되면
  {
     Serial.print("대상 온도 : ");
     Serial.print(CalcTemp(_rawObject));   // 대상온도 출력
     Serial.print("    센서 온도 : ");
     Serial.println(CalcTemp(_rawSensor));     // 센서온도 출력
     delay(0);                              // Delay 최소 100ms 이상
  }
  else
  {
    Serial.println("Please check the connection or I2C Address.");
    delay(0);
  }
}

float CalcTemp(int rawTemp)                    // 온도계산
{
  float retTemp;
  retTemp = float(rawTemp)*0.02;
  retTemp -= 273.15;
  return retTemp; 
}
uint8_t GetObject(void)                   // 대상온도 읽기
{
  int16_t rawObj;
  if(I2CReadWord(TB_REG_OBJECT, &rawObj))
  {
    if (rawObj & 0x8000)                         // 최상위 비트가 1 이면 에러
    {
      return 0;
    }
    _rawObject = rawObj;
    return 1;
  }
}
uint8_t GetSensor(void)                    // 센서온도 읽기 
{
  int16_t rawSen;
  if(I2CReadWord(TB_REG_SENSOR, &rawSen) )
  {
    _rawSensor = rawSen;
    return 1;
  }
  return 0;
}

uint8_t I2CReadWord(uint8_t reg, int16_t *dest)
{
  uint8_t BUF[5], Low_Byte, High_Byte, PEC;

  Wire.beginTransmission(_deviceAddress);
  Wire.write(reg);
   
  Wire.endTransmission(false);             // Send restart
  Wire.requestFrom(_deviceAddress, (uint8_t)3);// 3Byte Read
   
  Low_Byte = Wire.read();                  // Low Byte 저장
  High_Byte = Wire.read();                 // High Byte 저장
  PEC = Wire.read();                       // 센서에서 전송한 PEC 값
  
  //PEC 연산을 위한 데이터 저장
  BUF[0] = _deviceAddress<<1;
  BUF[1] = reg;
  BUF[2] = (_deviceAddress<<1) | 0x01;
  BUF[3] = Low_Byte;
  BUF[4] = High_Byte;

  if (PEC == CalPEC(BUF, 5))              // 센서가 전송한 PEC 값이 맞는지 검증
  {
    *dest = (High_Byte<<8) | Low_Byte;    // 에러가 없으면 온도 데이터 저장
    return 1;
  }
  return 0;                               // 실패시 0 return
}

uint8_t CalPEC(uint8_t *crc, uint8_t nBytes)  // PEC 연산
{
  uint8_t data, count;
  uint16_t remainder = 0;
    
  for(count=0; count<nBytes; ++count)
  {
     data = *(crc++) ^ remainder;
     remainder = crc8_table[data] ^ (remainder >> 8);
  }
  return (uint8_t)remainder;

}

void Motor (boolean DIR, byte Motorspeed) { //펌프의 모터를 제어를 위해 정의한 함수, Motor(HIGH 또는 LOW, 0~255의 출력)
  analogWrite(PWMpin, (DIR) ? (255 - Motorspeed) : Motorspeed);
  digitalWrite(DIRpin, DIR);
}