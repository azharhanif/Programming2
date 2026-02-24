public class DiscountRate {

    private static final double serviceDiscountPremium = 0.20;
    private static final double serviceDiscountGold = 0.15;
    private static final double serviceDiscountSilver = 0.10;

    private static final double productDiscountPremium = 0.10;
    private static final double productDiscountGold = 0.10;
    private static final double productDiscountSilver = 0.10;

    public static double getServiceDiscountRate(String type) {
        if (type == null) return 0;
        switch (type.toLowerCase()) {
            case "premium": return serviceDiscountPremium;
            case "gold": return serviceDiscountGold;
            case "silver": return serviceDiscountSilver;
            default: return 0;
        }
    }

    public static double getProductDiscountRate(String type) {
        if (type == null) return 0;
        switch (type.toLowerCase()) {
            case "premium": return productDiscountPremium;
            case "gold": return productDiscountGold;
            case "silver": return productDiscountSilver;
            default: return 0;
        }
    }
}
