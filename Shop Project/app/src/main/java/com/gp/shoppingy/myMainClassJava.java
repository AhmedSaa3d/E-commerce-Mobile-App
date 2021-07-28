package com.gp.shoppingy;


public class myMainClassJava {

    public static int loginId;
    public static int[] selectedItemsIds;
    public static int[] selectedItemsCounts;
    public static int[] selectedItemsPrice;
    public static int indx;
    public static int productind;

    public static String typeSearch;
    public static String searchContent;


    public static  void setLogin(int id)
    {
        loginId = id;
    }
    public static int getLoginId()
    {
        return loginId;
    }
    public static  void setProductind(int id)
    {
        productind = id;
    }
    public static int getProductind()
    {
        return productind;
    }



    public static void initDataFirstLogin()
    {
        selectedItemsIds = new int[100];
        selectedItemsCounts = new int[100];
        selectedItemsPrice = new int[100];
        typeSearch = "A";
        searchContent = "";
        indx = 0;
        for(int i=0;i<100;i++)
        {  selectedItemsCounts[i] = 0;
            selectedItemsPrice[i] = 0;
        }
    }
    public static void addSelectid(int id)
    {
        boolean ok =false;
        for(int i=0;i<indx;i++)
            if(selectedItemsIds[i]== id) {
                selectedItemsCounts[i]++;
              //  selectedItemsPrice[i] +=price;
                ok =true;
            }
        if(!ok)
        {
            selectedItemsIds[indx] = id;
            selectedItemsCounts[indx]++;
            indx++;
        }
    }

    public static void incQuantity(int ind)
    {
        selectedItemsCounts[ind]++;
    }
    public static void decQuantity(int ind)
    {
        selectedItemsCounts[ind]--;
    }


    public static int getId(int ind)
    {
        return selectedItemsIds[ind];
    }
    public static int getCount(int ind){
        return selectedItemsCounts[ind];
    }
    public static int getprice(int ind){
        return selectedItemsPrice[ind];
    }

    public static void setPrice(int ind,int price)
    {
        selectedItemsPrice[ind] = price;
    }

    public static int getSubTotal(int ind)
    {
        return (selectedItemsPrice[ind] * selectedItemsCounts[ind]);
    }
    public static int getAllTotal()
    {
        int total = 0;
        for(int i=0;i<indx;i++)
            total += getSubTotal(i);

        return  total;
    }

}
