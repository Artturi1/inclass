public class TemperatureConverter {

    public static void main(String[] args) {
        double fahrenheit = args.length == 0 ? 32 : Double.parseDouble(args[0]);
        TemperatureConverter converter = new TemperatureConverter();
        System.out.println(fahrenheit + " F = " + converter.fahrenheitToCelsius(fahrenheit) + " C");
    }

    public double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public boolean isExtremeTemperature(double celsius) {
        return celsius < -40 || celsius > 50;
    }
}
