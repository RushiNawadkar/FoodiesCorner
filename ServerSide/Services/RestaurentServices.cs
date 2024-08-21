using FindNearByReastaurents.Respository;
using FindNearByReastaurents.Entities;

namespace FindNearByReastaurents.Services
{
    public class RestaurentServices : IRestaurentServices
    {
        public List<Restaurents> GetRestaurents ()
        {
            IRestaurentRepo repo = new RestaurentRepo ();
            return repo.GetRestaurents ();
        }
    }
}
