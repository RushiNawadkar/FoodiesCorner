using FindNearByReastaurents.Entities;
using MySql.Data.MySqlClient;


namespace FindNearByReastaurents.Respository
{
    public class RestaurentRepo : IRestaurentRepo
    {
        public List<Restaurents> GetRestaurents()
        {
            List<Restaurents> restaurents = new List<Restaurents>();
            string connString = @"server=localhost;port=3306;user=root;password=iacsd@123;database=restaurents;";
            MySqlConnection connection = new MySqlConnection(connString);
            string query = "select * from restaurants";
            try
            {
                connection.Open();
                MySqlCommand cmd = new MySqlCommand(query, connection);
                MySqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    int id = int.Parse(reader["Id"].ToString());
                    string name = reader["RestaurantName"].ToString();
                    string address = reader["RestaurantAddress"].ToString();
                    double latitude = double.Parse(reader["Latitude"].ToString());
                    double longitude = double.Parse(reader["Longitude"].ToString());

                    Restaurents rest = new Restaurents();
                    rest.Id = id;
                    rest.RestaurentName = name;
                    rest.RestaurentAddress = address;
                    rest.Longitude = longitude;
                    rest.Latitude = latitude;
                    restaurents.Add(rest);
                }
                reader.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                connection.Close();
            }
            return restaurents;
        }
    }
}
            