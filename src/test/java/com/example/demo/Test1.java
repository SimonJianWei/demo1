package com.example.demo;

public class Test1 {

    //j 下标  x 数的下标  z   计数器
    public static  Integer  loopNum(int [] nums ,int z,int x ,int j ){
        if(j>=nums.length && z<=nums.length/2){
            return  null;
        }else if(j<nums.length && z >nums.length/2){
            return nums[x];
        }else if(z>nums.length/2){
            return nums[x];
        }
        Integer  res=loopNum(nums,nums[j]==nums[x]?z+=1:z,x,j+=1);
        return res;
    }

    public static void main(String[] args) {
        Integer res =null;
        int []  nums ={2,1,4,4,3,3,4,4,4,4,4,4,6,6,6,6,7,4,4};
        if(nums.length==1){
            res=nums[0];
        }
        int l=nums.length%2==0?nums.length/2: nums.length==1?1:nums.length/2+1;
        for(int i=0;i<l;i++){
            res=loopNum(nums,0,i,1);
            if(res!=null){
                 break;
            }
        }
        System.out.println(res==null?0:res.toString());
    }
}
