using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using FindNearByReastaurents.Services;

namespace FindNearByReastaurents.Controllers
{
    [Route("api/[controller]")]
    [ApiController]

    public class RestaurentController : ControllerBase
    {
        private IRestaurentServices _restaurentServices;

        public RestaurentController(IRestaurentServices restaurentServices)
        {
            _restaurentServices = restaurentServices;
        }

        [HttpGet]
        public IActionResult GetRestaurents()
        {
            return Ok(_restaurentServices.GetRestaurents());
        }
    }
}
