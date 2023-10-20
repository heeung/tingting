package com.alsif.tingting.global.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
public class DummyList {

	private final List<String> performers = Arrays.asList("방탄소년단", "임영웅", "뉴진스", "블랙핑크", "강다니엘", "에스파", "싸이", "아이브", "이찬원", "세븐틴", "(여자)아이들", "르세라핌", "김호중", "피프티피프티", "영탁", "NCT", "아이유", "엑소", "이영지", "송가인", "장민호", "태연", "오마이걸", "소녀시대", "프로미스나인", "트와이스", "정동원", "나훈아", "레드벨벳", "박재범", "노을", "화사", "김희재", "백현", "빅뱅", "하이라이트", "적재", "테이", "성시경", "조용필", "송민호", "백예린", "10CM", "잔나비", "이승윤", "경서", "조이", "서동현", "멜로망스", "박창근", "이무진", "박효신", "스테이씨", "스윙스", "바이브", "최예나", "폴킴", "현아", "마마무", "지코", "청하", "윤하", "이솔로몬", "카더가든", "선미", "임한별", "헤이즈", "정승환", "다비치", "케플러", "정은지", "케이시", "김필", "워너원", "이홍기", "볼빨간사춘기", "전소미", "호미들", "백지영", "SG워너비", "박혜원", "규현", "황치열", "권진아", "양요섭", "로꼬", "마크툽", "경서예지", "이병찬", "정동하", "먼데이키즈", "창모", "기리보이", "주호", "김승민", "우원재", "원슈타인", "장범준", "악동뮤지션", "한동근");

	private final List<String> performersImage = Arrays.asList(
		"https://cloudfront-us-east-1.images.arcpublishing.com/infobae/EOKA7NCX6REIHICYCTR53H2ORA.png",
		"https://cdn.topstarnews.net/news/photo/202310/15410847_1193005_5026.jpg",
		"https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/NewJeans_X_OLENS_1_%28cropped%29.jpg/800px-NewJeans_X_OLENS_1_%28cropped%29.jpg",
		"https://www.sisajournal.com/news/photo/202210/247637_160771_209.jpg",
		"https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/170923_KCON_2017_Australia_%28cropped%29.jpg/250px-170923_KCON_2017_Australia_%28cropped%29.jpg",
		"https://cdn.psnews.co.kr/news/photo/202209/2013645_57503_479.jpg",
		"https://wimg.mk.co.kr/meet/neds/2021/01/image_readtop_2021_56687_16109677744511536.jpg",
		"https://img.seoul.co.kr//img/upload/2023/03/22/SSC_20230322001650.jpg",
		"https://www.ekn.kr/mnt/file/202305/2023051801001004400048411.jpg",
		"https://newsimg.sedaily.com/2019/11/12/1VQRPADKLV_1.jpg",
		"https://img.khan.co.kr/news/2022/10/25/news-p.v1.20221025.09d1f78564e045e392769bb73ee6777d_P1.jpg",
		"https://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2023/05/06/2b5a4d7e-890a-4e17-8198-85c6b3a78485.jpg",
		"https://cdn.sisamagazine.co.kr/news/photo/202205/446667_451810_4629.jpg",
		"https://img.khan.co.kr/news/2023/08/30/news-p.v1.20230830.92a74a9e06df415ca82cc2fe2515793c_P1.png",
		"https://cdn.dailycc.net/news/photo/202308/752359_654213_3720.jpg",
		"https://file.mk.co.kr/meet/neds/2021/12/image_readtop_2021_1211895_16406540774897671.jpg",
		"https://dimg.donga.com/wps/NEWS/IMAGE/2023/01/02/117259889.2.jpg",
		"https://file2.nocutnews.co.kr/newsroom/image/2023/06/08/202306081613112675_0.jpg",
		"https://dimg.donga.com/wps/NEWS/IMAGE/2022/05/11/113339874.2.jpg",
		"https://img.hankyung.com/photo/202205/01.29961755.1.jpg",
		"https://img.hankyung.com/photo/202006/01.23019730.1.jpg",
		"https://img.gqkorea.co.kr/gq/2023/08/style_64d9b559a9863-934x1400.jpg",
		"https://image.ytn.co.kr/general/jpg/2022/0331/202203310920184109_d.jpg",
		"https://img1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/b5HD/image/xUPuBWeiqPusRjC5QBov9Loz6O8.jpg",
		"https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/YSPWWPQ6XNAV35L4YDGFFVVHJE.jpg",
		"https://img.sbs.co.kr/newsnet/etv/upload/2022/08/25/30000785929_1280.jpg",
		"https://newsimg.hankookilbo.com/cms/articlerelease/2021/08/18/bb4d2185-6c1d-418f-b46b-eca2390228d3.jpg",
		"https://thumb.mt.co.kr/06/2020/10/2020102709411713469_1.jpg/dims/optimize/",
		"https://file2.nocutnews.co.kr/newsroom/image/2018/11/03/20181103154544539681_0_750_1050.jpg",
		"https://file2.nocutnews.co.kr/newsroom/image/2020/03/09/20200309113055658289_0_800_1212.jpg",
		"https://newsimg.sedaily.com/2022/10/27/26CIIHC0OE_1.jpg",
		"https://ilyo.co.kr/contents/article/images/2018/1129/1543480540728261.jpg",
		"https://cdn.edpl.co.kr/news/photo/202306/9406_15529_5126.png",
		"https://spnimage.edaily.co.kr/images/Photo/files/NP/S/2020/05/PS20052600070.jpg",
		"https://img.sbs.co.kr/newsnet/etv/upload/2016/03/08/30000524178_1280.jpg",
		"https://img.wowtv.co.kr/wowtv_news/dnrs/20221121/2022112108422100503d3244b4fed182172185139.jpg",
		"https://newsimg.sedaily.com/2019/12/23/1VS6QQMOER_1.jpg",
		"https://image.ytn.co.kr/general/jpg/2023/0417/202304171152140089_d.jpg",
		"https://images.khan.co.kr/article/2023/08/09/news-p.v1.20230809.b12b56160ad1498591953930d808fa14_P1.jpg",
		"https://image.newsis.com/2018/04/11/NISI20180411_0013981137_web.jpg",
		"https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Song_Min-ho_at_Style_Icon_Asia_2016.jpg/640px-Song_Min-ho_at_Style_Icon_Asia_2016.jpg",
		"https://img.hankyung.com/photo/202111/BF.28129524.1.jpg",
		"https://img.sbs.co.kr/newsnet/etv/upload/2023/05/31/30000851160_500.jpg",
		"https://image.edaily.co.kr/images/Photo/files/NP/S/2019/05/PS19052600042.jpg",
		"https://monthly.chosun.com/up_fd/Mdaily/2021-04/bimg_thumb/KakaoTalk_20210414_190833051.jpg",
		"https://spnimage.edaily.co.kr/images/Photo/files/NP/S/2022/11/PS22112000013.jpg",
		"https://images.chosun.com/resizer/9LLMVHQWI8LZjVOi6RtV8DyWz1g=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/4III7T42AMZS42MK6XFXCIJGAI.jpg",
		"https://images.khan.co.kr/article/2023/06/07/news-p.v1.20230607.65aa5f72e8834d97af9dff501fb03318_P1.jpg",
		"https://www.kyongbuk.co.kr/news/photo/201808/1034574_315286_0923.jpg",
		"https://thumb.mt.co.kr/06/2022/09/2022090608007253624_1.jpg",
		"https://img.sbs.co.kr/newsnet/etv/upload/2021/05/14/30000688355_1280.jpg",
		"https://thumb.mt.co.kr/06/2023/06/2023061214462485648_1.jpg",
		"https://file.mk.co.kr/meet/neds/2022/02/image_readtop_2022_164533_16454180444952805.jpg",
		"https://file.mk.co.kr/meet/neds/2021/01/image_readtop_2021_53569_16109316394510455.jpg",
		"https://www.bntnews.co.kr/data/bnt/image/201712/3587d259a0614ef9592988a63e252e91.jpg",
		"https://image.mediapen.com/news/202111/news_677917_1636650251_m.jpg",
		"https://sccdn.chosun.com/news/html/2018/09/28/2018092901002255800179391.jpg",
		"https://image.newsis.com/2022/07/20/NISI20220720_0001046437_web.jpg",
		"https://file2.nocutnews.co.kr/newsroom/image/2022/10/11/202210111959151100_0.jpg",
		"https://yt3.googleusercontent.com/fOZFpVFz1FLNObkKlrsq4zK9a-6wQZi71vXnU9c-XFsplKe4UJZGtSvDmindq9UQlVgyZJo=s900-c-k-c0x00ffffff-no-rj",
		"https://cdn.fneyefocus.com/news/photo/201812/12826_15868_0928.jpg",
		"https://img.hankyung.com/photo/202211/AKR20221104074300005_02_i_P4.jpg",
		"https://img.vogue.co.kr/vogue/2022/03/style_6232a2c8601b2-620x930.jpeg",
		"https://www.nbnnews.co.kr/news/photo/202211/716258_717681_5731.jpg",
		"https://www.jinsiltimes.org/news/photo/202206/15952_23047_101.jpg",
		"https://image.ajunews.com/content/image/2018/12/09/20181209192451318312.jpg",
		"https://www.kukinews.com/data/kuk/image/2021/05/20/kuk202105200360.680x.0.jpeg",
		"https://file.mk.co.kr/meet/yonhap/2021/05/19/image_readtop_2021_482937_0_142111.jpg",
		"https://img.sbs.co.kr/newsnet/etv/upload/2019/12/04/30000638943_1280.jpg",
		"https://file.mk.co.kr/meet/neds/2022/01/image_readtop_2022_83245_16432617644931013.jpg",
		"https://image.news1.kr/system/photos/2023/9/1/6188902/article.jpg/dims/optimize",
		"https://images.chosun.com/resizer/q8un4MKUHblG0sje-wx3zy4S4b0=/530x790/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/NNJQMDAZ6QZ5IH7QURFSDFNYOA.jpg",
		"https://img.sbs.co.kr/newsnet/etv/upload/2019/12/02/30000638816_1280.jpg",
		"https://img.khan.co.kr/news/2018/03/19/l_2018032101002355300185971.jpg",
		"https://spnimage.edaily.co.kr/images/Photo/files/NP/S/2022/11/PS22110600301.jpg",
		"https://newsimg.sedaily.com/2022/04/20/264RMU0PBF_1.jpg",
		"https://image.news1.kr/system/photos/2023/7/24/6119716/article.jpg/dims/quality/80/optimize",
		"https://image-cdn.hypb.st/https%3A%2F%2Fkr.hypebeast.com%2Ffiles%2F2021%2F12%2Fhomies-korean-hiphop-generation-album-release-interview-2021-ft-1.jpg?w=960&cbr=1&q=90&fit=max",
		"https://isplus.com/data/isp/image/2019/09/18/isphtm_201909189455235451.jpg",
		"https://img.etnews.com/news/article/2023/03/15/cms_temp_article_15115407235563.jpg",
		"https://img.etnews.com/news/article/2022/11/24/article_24084220274076.jpg",
		"https://img1.yna.co.kr/etc/inner/KR/2019/05/07/AKR20190507087800005_02_i_P2.jpg",
		"https://file.mk.co.kr/meet/neds/2022/05/image_readtop_2022_426504_16525196395042587.jpg",
		"https://file.mk.co.kr/meet/neds/2021/03/image_readtop_2021_198169_16146360614555129.jpg",
		"https://img.sbs.co.kr/newsnet/etv/upload/2021/09/06/30000711626_1280.jpg",
		"https://img.sbs.co.kr/newsnet/etv/upload/2018/09/07/30000612546_700.jpg",
		"https://img.sbs.co.kr/newsnet/etv/upload/2019/11/27/30000638602_1280.jpg",
		"https://img.wowtv.co.kr/wowtv_news/dnrs/20210714/2021071408394105711d3244b4fed182172186127.jpg",
		"https://images.chosun.com/resizer/ljjwuXNgmpbbHXLZR4ORT0Liu1w=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/QFM4XBWQXVB4BFSUWJZDMSOPSA.jpg","https://cdn.onews.tv/news/photo/202201/108362_122532_3147.jpg",
		"https://img.sbs.co.kr/newsnet/etv/upload/2012/11/01/30000176092_1280.jpg",
		"https://img.kbs.co.kr/kbs/620/news.kbs.co.kr/data/fckeditor/new/image/170514_chang01.jpg",
		"https://file2.mk.co.kr/meet/neds/2022/06/image_readtop_2022_518631_16551726415074656.jpg",
		"https://www.madtimes.org/news/photo/202204/12192_27448_289.jpg",
		"https://dimg.donga.com/wps/NEWS/IMAGE/2021/01/31/105199941.2.jpg",
		"https://img.marieclairekorea.com/2022/12/mck_63b5441a04650.jpg",
		"https://file.mk.co.kr/meet/neds/2021/12/image_readtop_2021_1115914_16386164654872894.jpg",
		"https://file2.nocutnews.co.kr/newsroom/image/2018/09/07/20180907144024199289_0_550_550.jpg",
		"https://wimg.mk.co.kr/meet/neds/2019/10/image_readtop_2019_783017_15698844653918925.jpg",
		"https://dimg.donga.com/wps/NEWS/IMAGE/2019/12/26/98967814.2.jpg"
		);

	private final List<String> concertHallName = Arrays.asList(
		"예술의 전당 오페라하우스",
		"COEX 오디토리움",
		"예스24 라이브홀",
		"3.15아트센터 대극장",
		"문화예술회관 대공연장",
		"블루스퀘어 마스터카드홀",
		"문화의전당",
		"아트센터 콘서트홀",
		"EXCO 컨벤션홀",
		"충무체육관",
		"올림픽핸드볼경기장",
		"유관순체육관");

	private final List<String> concertHallCity = Arrays.asList(
		"서울",
		"부산",
		"대전",
		"대구",
		"광주",
		"울산",
		"인천",
		"경북"
	);


}
