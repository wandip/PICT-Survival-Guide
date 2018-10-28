
# coding: utf-8

# In[131]:


import pandas as pd


# In[132]:


data = pd.read_csv("iris.csv", index_col = 0)
data.head(10)


# In[133]:


data['Species'].unique()


# In[134]:


len(data)


# In[135]:


featurearray = data.columns[0:4]
featurearray


# In[136]:


len(featurearray)


# In[137]:


for i in data.columns[0:4]:
    print(i)
    print("Data type:- ", data[i].dtype)
    print("Min:- ", data[i].min())
    print("Max:- ", data[i].max())
    print("Range:- ", data[i].min() - data[i].max())
    print("Mean:- ", data[i].mean())
    print("Variance:- ", data[i].var())
    print("Standard Deviation:- ", data[i].std())
    print("Precentile:- ", data[i].quantile(0.5))
    print("-"*40)


# In[138]:


data.hist(column = data.columns[0:4], figsize = (15,15))
plt.suptitle("Histogram")


# In[139]:


data.boxplot(column = list(data.columns[0:4]), figsize = (10,10))
plt.suptitle("Boxplot")

