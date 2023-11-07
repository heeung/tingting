import SwiperCore from "swiper";
import { Navigation, Pagination, Scrollbar, Autoplay } from 'swiper/modules';
import { Swiper, SwiperSlide } from "swiper/react";
import { useRef } from 'react';
import styles from './MyCarousel.module.css';

// Import Swiper styles
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/scrollbar';
import 'swiper/css/autoplay';

export default function MainCarousel() {

  const images = [
    "http://ticketimage.interpark.com/TCMS3.0/NMain/BbannerPC/2310/231011114905_23012217.gif",
    "http://ticketimage.interpark.com/TCMS3.0/NMain/BbannerPC/2310/231017102022_23014431.gif",
    "http://ticketimage.interpark.com/TCMS3.0/NMain/BbannerPC/2310/231017114911_23014204.gif",
    "http://ticketimage.interpark.com/TCMS3.0/NMain/BbannerPC/2310/231018114336_23014703.gif",
    "http://ticketimage.interpark.com/TCMS3.0/NMain/BbannerPC/2310/231020093025_23010643.gif"
  ]
  SwiperCore.use([Navigation, Scrollbar]);
  const swiperRef = useRef<SwiperCore>();
  const breakpoints = {
    768: {
      slidesPerView: 1,
      slidesPerGroup: 1,
    },
    1024: {
      slidesPerView: 1,
      slidesPerGroup: 1,
    },
    1200: {
      slidesPerView: 1,
      slidesPerGroup: 1,
    },
  };

 return (
    <Swiper
      onSwiper={(swiper) => {
        swiperRef.current = swiper;
      }}
      breakpoints={breakpoints}
      className={styles.card}
      modules={[Navigation, Pagination, Scrollbar, Autoplay]}
      spaceBetween={30}
      slidesPerView={1}
      scrollbar={{ draggable: true }}
      centeredSlides={true}
      autoplay={{
        delay: 2000,
        disableOnInteraction: false
      }}
      pagination={{
        clickable: true
      }}
      navigation={true}
    >
      {images?.map((image) => (
        <SwiperSlide 
          className={styles.card}
          key={image}>
          <img
            src={image}
          />
        </SwiperSlide>
      ))}
    </Swiper>
  );
}