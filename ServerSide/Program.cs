using FindNearByReastaurents.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
builder.Services.AddScoped<IRestaurentServices, RestaurentServices>();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
//Cross Origin Site Scripting
builder.Services.AddCors();

var app = builder.Build();

app.UseHttpsRedirection();
app.UseCors(options => 
    options.WithOrigins("http://localhost:3000")
    .AllowAnyHeader()
    .AllowAnyMethod()
//.AllowCredentials()  //necessary if your API uses cookies or authentication tokens.
);

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthorization();

app.MapControllers();

app.Run();
