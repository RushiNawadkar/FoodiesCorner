import Offers from "@/components/offers";
import About from "@/components/about";
import Bookings from "@/components/bookings";
import Testimonials from "@/components/testimonials";
import Hero from "@/components/hero";
import Restaurants from "@/components/restaurants";

export default function Home() {
  return (
    <>
      <Hero />
      <Offers />
      <Restaurants comp="Home" />
      <About />
      <Bookings />
      <Testimonials />
     
    </>

  );
}
