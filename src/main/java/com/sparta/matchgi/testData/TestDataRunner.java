package com.sparta.matchgi.testData;

import com.sparta.matchgi.RedisRepository.RedisChatRepository;
import com.sparta.matchgi.model.*;
import com.sparta.matchgi.repository.*;
import com.sparta.matchgi.util.converter.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class TestDataRunner implements ApplicationRunner {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoomRepository roomRepository;

    private final UserRoomRepository userRoomRepository;

    private final ImageRepository imageRepository;

    private final RedisChatRepository redisChatRepository;
    private final  ChatRepository chatRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User testUser1 = new User("nickname2","email2", passwordEncoder.encode("password2"));

        userRepository.save(testUser1);

        createData(testUser1);


    }



    private void createData(User testUser1) {


        for(int i =1; i<=10; i++){

            Long id=(long)i;

            String title =getRandomString();

            User user = testUser1;

            String content = getRandomString();

            int viewCount = 0;

            int requestCount = 0;

            double lat;

            double lng;

            int latnum=i;
            switch (latnum){
                case 1:lat=35.89064935862601;lng=128.6121073156588;break;//경북대학교
                case 2:lat=35.88298540137134;lng=128.61376795659538;break;//대구공고
                case 3:lat=35.905792656554134;lng=128.61715517604347;break;//코스트코
                case 4:lat=35.88398926233881;lng=128.62486167276103;break;//파티마병원
                case 5:lat=35.87125226706986;lng=128.62048289351063;break;//신천초
                case 6:lat=33.458832173698866;lng=126.9408389117477;break;//제주도-성산일출봉
                case 7:lat=37.567427873950024;lng=126.97867472484094;break;//서울-서울시청
                case 8:lat=36.0540405039569;lng=129.3784666991711;break;//포항-영일대
                case 9:lat=35.1688135372758;lng=129.0582845214676;break;//부산-부산시민공원
                default:lat=34.840214358040356;lng=127.61343090283592;break;//여수-여수공항
            }


            int subjectNumber = getRandomInt(7,1);

            String address = getRandomString();

            SubjectEnum subject;

            MatchStatus matchStatus;
            int matchnum=getRandomInt(2,1);
            switch (matchnum){
                case 1: matchStatus=MatchStatus.ONGOING;break;
                default: matchStatus=MatchStatus.MATCHEND;break;
            }


            switch (subjectNumber){
                case 1:
                    subject = SubjectEnum.SOCCER;
                    break;
                case 2:
                    subject = SubjectEnum.BADMINTON;
                    break;
                case 3:
                    subject = SubjectEnum.BASKETBALL;
                    break;
                case 4:
                    subject = SubjectEnum.BILLIARDS;
                    break;
                case 5:
                    subject = SubjectEnum.BOWLING;
                    break;
                case 6:subject = SubjectEnum.TENNIS;
                    break;
                default: subject=SubjectEnum.ETC;break;


            }
            int day = getRandomInt(10,3);


            LocalDateTime matchDeadline = getRandomDeadline(day+1);

            int owner=-1;



            Post post = new Post(id,user,title,matchDeadline,content,subject,lat,lng,viewCount,requestCount, matchStatus,address,null);

            postRepository.save(post);

            ImgUrl imgUrl =new ImgUrl(post,"2f133003-1c1f-4329-a02b-8619c89e385a_KakaoTalk_Image_2022-07-26-01-35-18.png","https://yougeun-bucket.s3.ap-northeast-2.amazonaws.com/2f133003-1c1f-4329-a02b-8619c89e385a_KakaoTalk_Image_2022-07-26-01-35-18.png");

            imageRepository.save(imgUrl);


            Room room = new Room(post.getId(),user,post);

            roomRepository.save(room);

            UserRoom userRoom = new UserRoom(user,room,DateConverter.millsToLocalDateTime(System.currentTimeMillis()));

            userRoomRepository.save(userRoom);

            RedisChat redisChat=new RedisChat("1",room,"안녕하세요",user,getRandomDeadline(day+1));
            redisChatRepository.save(redisChat);

            Chat chat=new Chat(room, "네 안녕하세요", user,getRandomDeadline(day+1));
            chatRepository.save(chat);


        }

    }

    private String getRandomString(){
        Random random = new Random();

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;

        String randomString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return randomString;
    }

    private int getRandomInt(int bound, int num) {
        Random random = new Random();

        return random.nextInt(bound) + num;
    }

    private LocalDateTime getRandomDeadline(int day){


        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DATE,day);


        return DateConverter.dateToLocalDateTime(cal.getTime());
    }
}